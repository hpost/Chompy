package cc.femto.kommon.mvp

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import butterknife.bindView
import cc.femto.chompy.R
import cc.femto.chompy.util.snackError
import cc.femto.kommon.extensions.snack
import cc.femto.kommon.ui.widget.BetterViewAnimator
import com.jakewharton.rxbinding.view.clicks
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

abstract class MvpContentLayout<T, V : MvpContentView<T>, P : MvpPresenter<V>>(context: Context, attrs: AttributeSet)
: CoordinatorLayout(context, attrs), MvpContentView<T> {

    abstract protected val presenter: P

    val viewAnimator: BetterViewAnimator by bindView(R.id.view_animator)
    val errorText: TextView by bindView(R.id.error_message)
    val retryButton: Button by bindView(R.id.btn_retry)

    val subscriptions = CompositeSubscription()

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isInEditMode) return
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isInEditMode) return

        // retry button
        subscriptions += retryButton.clicks().throttleFirst(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { loadData(false) }

        @Suppress("UNCHECKED_CAST") presenter.onAttach(this as V)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscriptions.clear()
        presenter.onDetach()
    }

    override fun showLoading(pullToRefresh: Boolean) {
        viewAnimator.displayedChildId = R.id.loading_view
    }

    override fun showContent() {
        viewAnimator.displayedChildId = R.id.content_view
    }

    override fun showEmpty() {
        viewAnimator.displayedChildId = R.id.empty_view
    }

    override fun showError(msg: String) {
        errorText.text = msg
        viewAnimator.displayedChildId = R.id.error_view
    }

    override fun confirmation(msg: String) {
        snack(msg)
    }

    override fun error(msg: String) {
        snackError(msg)
    }
}