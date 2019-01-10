package blockchainsample.android.com.blockchain_sample_kotlin.presentation.fragments.exchange_rate.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v4.util.Pair
import blockchainsample.android.com.blockchain_sample_kotlin.R
import blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules.IRecyclerItemClickListener
import info.blockchain.api.exchangerates.Currency

class ExchangeRateAdapter(private var itemClickListener: IRecyclerItemClickListener<String>?) :
    RecyclerView.Adapter<ExchangeRateVH>() {

    private var listData: MutableList<Pair<String, Currency>> = ArrayList()
    private var selectedPosition: Int=-1

    fun updateData(listData: List<Pair<String, Currency>>){
        this.listData.addAll(listData)
        notifyItemRangeInserted(0, this.listData.size)
    }

    fun selectItem(pos: Int){
        val prevPosititon: Int= this.selectedPosition
        this.selectedPosition = pos
        notifyItemChanged(prevPosititon)
        notifyItemChanged(this.selectedPosition)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ExchangeRateVH {
        return ExchangeRateVH(LayoutInflater.from(p0.context).inflate(R.layout.list_item_exchange_rate, p0, false),
            this.itemClickListener!!
        )
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(p0: ExchangeRateVH, p1: Int) {
        p0.bind(listData.get(p1), selectedPosition == p1)
    }

}