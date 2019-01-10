package blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.menu

import blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules.BasePresenter
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules.BaseView

interface MenuContract {
    interface MenuView: BaseView<MenuPresenter> {
        fun showExchangeRate()
    }
    interface MenuPresenter: BasePresenter {
        fun openExchangeRate()
    }

}