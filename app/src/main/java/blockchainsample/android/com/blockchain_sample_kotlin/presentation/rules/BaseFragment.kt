package blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules


import android.app.Activity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import blockchainsample.android.com.blockchain_sample_kotlin.R
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.MainActivity
import java.lang.ClassCastException
import java.lang.RuntimeException


abstract class BaseFragment : Fragment() {

    protected var activity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(layoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initPresenter()
    }

    //This method recommend call in onViewCreated() | initUI()
    @SuppressWarnings("unchecked")
    protected fun findView(@IdRes viewId: Int): View? {
        val fragmentView = this.view
        return if (fragmentView != null) {
            fragmentView.findViewById(viewId)
        }
        else
            null
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        try {
            this.activity= activity as MainActivity
            this.activity!!.title= screenName().toString()
        }catch (e: ClassCastException){
            throw RuntimeException("This fragment should have Activity instance")
        }
    }

    override fun onResume() {
        super.onResume()
        this.activity!!.title= getString(screenName())
    }

    @StringRes
    protected abstract fun screenName(): Int
    @LayoutRes
    protected abstract fun layoutRes(): Int
    protected abstract fun initUI()
    protected abstract fun initPresenter()


}
