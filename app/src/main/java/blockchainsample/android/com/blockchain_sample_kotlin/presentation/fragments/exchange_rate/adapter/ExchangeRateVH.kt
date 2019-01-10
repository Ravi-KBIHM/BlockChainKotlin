package blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.exchange_rate.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.support.v4.util.Pair
import blockchainsample.android.com.blockchain_sample_kotlin.R
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules.IRecyclerItemClickListener
import info.blockchain.api.exchangerates.Currency
import java.text.DecimalFormat
import java.text.NumberFormat

class ExchangeRateVH(itemView: View, private val itemClickListener: IRecyclerItemClickListener<String>) : RecyclerView.ViewHolder(itemView) {
    private lateinit var label: TextView
    private lateinit var sell: TextView
    private lateinit var buy: TextView

    private var numberFormat: NumberFormat= DecimalFormat.getInstance()
    private var keyLabel: String=""

    init {
        numberFormat.maximumFractionDigits=15
        numberFormat.minimumFractionDigits=2

        label=itemView.findViewById(R.id.tvExchangeLabel_LIER)
        sell=itemView.findViewById(R.id.tvSellValue_LIER)
        buy =itemView.findViewById(R.id.tvBuyValue_LIER)
        itemView.setOnClickListener({
            this.itemClickListener.selectItem(keyLabel, adapterPosition)
        })
    }

    fun bind(currencyPair: Pair<String, Currency>, isSelected: Boolean){
        itemView.isSelected= isSelected
        keyLabel= currencyPair.first!!

        label.text=  String.format("%s (%s)" , keyLabel, currencyPair.second!!.symbol)
        sell.text=  numberFormat.format(currencyPair.second!!.sell.toDouble())
        buy.text=  numberFormat.format(currencyPair.second!!.buy.toDouble())

    }

}