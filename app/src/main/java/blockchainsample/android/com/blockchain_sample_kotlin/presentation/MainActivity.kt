package blockchainsample.android.com.blockchain_sample_kotlin.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import blockchainsample.android.com.blockchain_sample_kotlin.R
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.exchange_rate.ExchangeRateFragment
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.menu.MenuFragment
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules.BaseFragment

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener(this)
        setContentView(R.layout.activity_main)
        replaceFragment(MenuFragment(), false)
    }

    fun replaceFragment(fragment: BaseFragment, addToBackStack: Boolean) {
        if (true){
            if (addToBackStack){
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_left)
                    .replace(R.id.flFragmentsContainer, fragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }else{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.flFragmentsContainer, fragment)
                    .commitAllowingStateLoss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeOnBackStackChangedListener(this)
    }

    override fun onBackStackChanged() {
        shouldDisplayHomeUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    fun shouldDisplayHomeUp(){
        val backExist: Boolean= supportFragmentManager.backStackEntryCount > 0
        if (supportActionBar !=null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(backExist)
    }
}
