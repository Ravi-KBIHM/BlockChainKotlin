package blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.exchange_rate


import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import blockchainsample.android.com.blockchain_sample_kotlin.R
import blockchainsample.android.com.blockchain_sample_kotlin.domain.BlockChainRepository
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.exchange_rate.adapter.ExchangeRateAdapter
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules.BaseFragment
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules.IRecyclerItemClickListener
import info.blockchain.api.exchangerates.Currency
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException


class ExchangeRateFragment : BaseFragment(), ExchangeRateContract.ExchangeRateView,
    View.OnClickListener, IRecyclerItemClickListener<String> {

    override fun setFromExchange(exchangeCode: String, value: Double) {
        if (exchangeCode.equals(ExchangeRatePresenter.BITCOIN)) {
            fromExchangeIconInText.visibility = View.INVISIBLE
            fromExchangeIcon!!.visibility = View.VISIBLE
        } else {
            fromExchangeIconInText.text = exchangeCode
            fromExchangeIconInText.visibility = View.VISIBLE
            fromExchangeIcon!!.visibility = View.INVISIBLE
        }
        fromExchangeValue.setText(numberFormat.format(value))
    }

    override fun setToExchange(exchangeCode: String, value: Double) {
        if (exchangeCode.equals(ExchangeRatePresenter.BITCOIN)) {
            toExchangeIconInText.visibility = View.INVISIBLE
            toExchangeIcon.visibility = View.VISIBLE
        } else {
            toExchangeIconInText.text = exchangeCode
            toExchangeIconInText.visibility = View.VISIBLE
            toExchangeIcon.visibility = View.INVISIBLE
        }
        toExchangeValue.setText(numberFormat.format(value))

    }

    private var presenter: ExchangeRateContract.ExchangeRatePresenter? = null
    private lateinit var fromExchangeIcon: ImageView
    private lateinit var toExchangeIcon: ImageView
    private lateinit var changeExchangeSidesIcon: ImageView
    private lateinit var fromExchangeIconInText: TextView
    private lateinit var toExchangeIconInText: TextView
    private lateinit var emptyListText: TextView
    private lateinit var fromExchangeValue: EditText
    private lateinit var toExchangeValue: EditText
    private lateinit var exchangeRatesRecycler: RecyclerView

    private var exchangeRateAdapter: ExchangeRateAdapter? = null
    private var progressDialog: ProgressDialog? = null
    private var numberFormat: NumberFormat = DecimalFormat.getInstance()

    override fun showProgress() {
        dismissProgress()
        progressDialog = ProgressDialog(activity)
        progressDialog!!.setMessage("Getting exchange rates... Please wait.")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    override fun dismissProgress() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }

    private val fromExchangeTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            var result: Double
            try {
                result = numberFormat.parse(charSequence.toString()).toDouble()
            } catch (e: ParseException) {
                result = 0.0
                e.printStackTrace()
            }

            presenter!!.calculateExchange(result)
        }

        override fun afterTextChanged(editable: Editable) {

        }
    }

    override fun showErrorToast() {
        Toast.makeText(activity, R.string.exchange_rate_error, Toast.LENGTH_SHORT).show()
        emptyListText.visibility = if (exchangeRateAdapter!!.itemCount == 0) View.VISIBLE else View.GONE
    }

    override fun setExchangeRates(data: List<Pair<String, Currency>>) {
        exchangeRateAdapter!!.updateData(data)
        emptyListText.visibility = if (exchangeRateAdapter!!.getItemCount() == 0) View.VISIBLE else View.GONE
    }


    override fun setPresenter(presenter: ExchangeRateContract.ExchangeRatePresenter) {
        this.presenter = presenter
        this.presenter!!.subscribe()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivChangeExchangeSides_FER -> presenter!!.changeExchangeSide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fromExchangeValue.removeTextChangedListener(fromExchangeTextWatcher)
        this.presenter!!.unsubscribe()
    }

    override fun selectItem(data: String, position: Int) {
        exchangeRateAdapter!!.selectItem(position)
        presenter!!.setTargetCurrencyInKey(data)
    }

    override fun screenName(): Int {
        return R.string.exchange_rate
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_exchange_rate
    }

    override fun initUI() {
        numberFormat.maximumFractionDigits = 15
        numberFormat.minimumFractionDigits = 2

        fromExchangeIcon = (findView(R.id.ivFromExchangeIcon_FER) as ImageView?)!!
        toExchangeIcon = (findView(R.id.ivToExchangeIcon_FER) as ImageView?)!!
        changeExchangeSidesIcon = (findView(R.id.ivChangeExchangeSides_FER) as ImageView?)!!

        fromExchangeIconInText = (findView(R.id.tvFromExchangeIconInText_FER) as TextView?)!!
        toExchangeIconInText = (findView(R.id.tvToExchangeIconInText_FER) as TextView?)!!
        emptyListText = (findView(R.id.tvListEmpty_FER) as TextView?)!!

        fromExchangeValue = (findView(R.id.etFromExchangeValue_FER) as EditText?)!!
        toExchangeValue = (findView(R.id.etToExchangeValue_FER) as EditText?)!!
        exchangeRatesRecycler = (findView(R.id.rvExchangeRates_FER) as RecyclerView?)!!

        exchangeRatesRecycler.layoutManager = LinearLayoutManager(activity)
        exchangeRateAdapter = ExchangeRateAdapter(this)
        exchangeRatesRecycler.adapter = exchangeRateAdapter

        fromExchangeValue.addTextChangedListener(fromExchangeTextWatcher)
        changeExchangeSidesIcon.setOnClickListener(this)
    }

    override fun initPresenter() {
        ExchangeRatePresenter(this, BlockChainRepository())
    }

}
