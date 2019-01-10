package blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.exchange_rate

import android.support.v4.util.Pair
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules.BaseModel
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules.BasePresenter
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules.BaseView
import info.blockchain.api.exchangerates.Currency
import rx.Observable

interface ExchangeRateContract {
    interface ExchangeRateView : BaseView<ExchangeRatePresenter> {
        fun showProgress()
        fun dismissProgress()
        fun showErrorToast()
        fun setExchangeRates(data: List<Pair<String, Currency>>)

        fun setFromExchange(exchangeCode: String, value:Double)
        fun setToExchange(exchangeCode: String, value:Double)
    }

    interface ExchangeRatePresenter : BasePresenter {
        fun setTargetCurrencyInKey(keyLabel: String)
        fun calculateExchange(valueFrom: Double)
        fun changeExchangeSide()
    }

    interface ExchangeRateModel: BaseModel {
        fun getExchangeRates(): Observable<Map<String, Currency>>
    }

}