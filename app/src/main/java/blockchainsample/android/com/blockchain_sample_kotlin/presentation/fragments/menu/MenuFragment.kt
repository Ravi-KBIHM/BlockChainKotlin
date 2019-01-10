package blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.menu

import android.view.View
import blockchainsample.android.com.blockchain_sample_kotlin.R
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.exchange_rate.ExchangeRateFragment
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules.BaseFragment

class MenuFragment: BaseFragment(), MenuContract.MenuView, View.OnClickListener {

    private var presenter: MenuContract.MenuPresenter? = null

    override fun screenName(): Int {
        return R.string.app_name
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_menu
    }

    override fun initUI() {
        findView(R.id.llExchangeRate_FM)!!.setOnClickListener(this)
    }

    override fun initPresenter() {
        MenuPresenter(this)
    }

    override fun showExchangeRate() {
        activity!!.replaceFragment(ExchangeRateFragment(), true)
    }

    override fun setPresenter(presenter: MenuContract.MenuPresenter) {
        this.presenter = presenter
        this.presenter!!.subscribe()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.llExchangeRate_FM -> presenter!!.openExchangeRate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.presenter!!.unsubscribe()
    }
}