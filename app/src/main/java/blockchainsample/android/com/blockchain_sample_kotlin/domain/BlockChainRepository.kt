package blockchainsample.android.com.blockchain_sample_kotlin.domain

import blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.exchange_rate.ExchangeRateContract
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.utils.Constants
import info.blockchain.api.exchangerates.Currency
import info.blockchain.api.exchangerates.ExchangeRates
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class BlockChainRepository: ExchangeRateContract.ExchangeRateModel {
    override fun getExchangeRates(): Observable<Map<String, Currency>> {
        return Observable.fromCallable<Map<String, Currency>> { ExchangeRates.getTicker(Constants.API_KEY) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())

    }

}