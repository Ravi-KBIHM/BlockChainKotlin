package blockchainsample.android.com.blockchain_sample_kotlin.presentation.rules

interface BaseView<P: BasePresenter> {
    fun setPresenter(presenter: P)
}