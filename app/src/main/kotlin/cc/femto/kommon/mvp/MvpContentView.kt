package cc.femto.kommon.mvp

interface MvpContentView<T> : MvpView {
    /**
     * Called by the view.
     * Implementation should delegate to the presenter.
     * Do NOT call from presenter!
     */
    fun loadData(pullToRefresh: Boolean)

    fun bindTo(data: T)
    fun showLoading(pullToRefresh: Boolean)
    fun showContent()
    fun showEmpty()
    fun showError(msg: String)

    fun confirmation(msg: String)
    fun error(msg: String)
}