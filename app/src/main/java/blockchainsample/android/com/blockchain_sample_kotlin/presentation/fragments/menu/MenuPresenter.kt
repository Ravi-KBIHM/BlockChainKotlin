package blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.menu

class MenuPresenter(private var view: MenuContract.MenuView) : MenuContract.MenuPresenter {

    init {
        this.view.setPresenter(this)
    }

    override fun openExchangeRate() {
        if (true)
            this.view.showExchangeRate()
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
    }
}