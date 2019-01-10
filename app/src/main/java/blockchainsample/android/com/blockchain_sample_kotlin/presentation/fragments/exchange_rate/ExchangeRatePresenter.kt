package blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.exchange_rate

import android.support.v4.util.Pair
import info.blockchain.api.exchangerates.Currency
import java.math.BigDecimal

class ExchangeRatePresenter(
    private var view: ExchangeRateContract.ExchangeRateView?,
    private var model: ExchangeRateContract.ExchangeRateModel?
) : ExchangeRateContract.ExchangeRatePresenter {

    companion object {
    var BITCOIN: String ="BTC"
    }

    private lateinit var exchangeRates: Map<String, Currency>
    private lateinit var fromExchange: Pair<String, Currency>
    private lateinit var toExchange: Pair<String, Currency>

    private var valueFrom: Double = 1.0
    private var valueTo: Double = 1.0

    init {
        this.view!!.setPresenter(this)
    }



    override fun setTargetCurrencyInKey(keyLabel: String) {
        if (fromExchange.first.equals(BITCOIN)){
            toExchange = Pair(keyLabel, exchangeRates.get(keyLabel))
            calculateExchange(valueFrom)
        }else{
            fromExchange = Pair(keyLabel, exchangeRates.get(keyLabel))
            this.view!!.setFromExchange(fromExchange.first!!, fromExchange.second!!.buy.toDouble())
        }
    }

    override fun calculateExchange(valueFrom: Double) {
        this.valueFrom=valueFrom
        if (fromExchange.first.equals(BITCOIN))
            valueTo=toExchange.second!!.buy.multiply(BigDecimal.valueOf(valueFrom)).toDouble()
        else
            valueTo= BigDecimal.valueOf(valueFrom).divide(fromExchange.second!!.buy, 15, BigDecimal.ROUND_HALF_UP).toDouble()
        this.view!!.setToExchange(toExchange.first!!, valueTo)
    }

    override fun changeExchangeSide() {
        val from: Pair<String, Currency> = Pair(fromExchange.first, fromExchange.second)
        fromExchange = toExchange
        toExchange = from
        this.view!!.setFromExchange(fromExchange.first!!, valueTo)
    }

    fun prepareDataForList(): List<Pair<String, Currency>>{
       val data: MutableList<Pair<String, Currency>> = ArrayList()
        for (label in exchangeRates.keys) {
            data.add(Pair(label, exchangeRates[label]))
        }
        return data
    }

    override fun subscribe() {
        fromExchange= Pair(BITCOIN, Currency(1.0, 1.0, 1.0, 1.0, BITCOIN))
        toExchange= Pair(BITCOIN, Currency(1.0, 1.0, 1.0, 1.0, BITCOIN))
        this.view!!.setFromExchange(fromExchange.first!!, fromExchange.second!!.buy.toDouble())

        loadExchanges()
    }

    fun loadExchanges(){
        view!!.showProgress()
        model!!.getExchangeRates().subscribe({ stringCurrencyMap ->
            if (this.view != null) {
                exchangeRates = stringCurrencyMap
                this.view!!.dismissProgress()
                this.view!!.setExchangeRates(prepareDataForList())
            }
        }, { throwable ->
            throwable.printStackTrace()
            if (this.view != null) {
                this.view!!.dismissProgress()
                this.view!!.showErrorToast()
            }
        })
    }

    override fun unsubscribe() {
        this.view = null
        this.model = null
    }
}