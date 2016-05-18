package cc.femto.kommon.mvp

import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.plusAssign
import java.util.concurrent.TimeUnit

open class MvpContentPresenter<T, V : MvpContentView<T>> : MvpBasePresenter<V>() {

    private var timerSubscription: Subscription? = null

    /**
     * Typically called before loading data.
     * Shows the loading view after a grace period to increase perceived speed.
     */
    protected fun deferLoading() {
        if (timerSubscription != null) return

        timerSubscription = Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view?.showLoading(false) }
        subscriptions += timerSubscription!! // unsubscribe timer in onDetach
    }

    /**
     * Needs to be called after data has been loaded.
     */
    protected fun onLoadingFinished() {
        timerSubscription?.unsubscribe()
        timerSubscription = null
    }
}