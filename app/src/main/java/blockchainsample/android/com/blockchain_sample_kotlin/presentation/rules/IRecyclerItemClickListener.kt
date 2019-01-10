package blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules

interface IRecyclerItemClickListener<T> {
    fun selectItem(data: T, position: Int)
}