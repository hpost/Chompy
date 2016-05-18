package cc.femto.chompy.data.api

import cc.femto.chompy.data.api.model.ListUberMenuRequest
import cc.femto.chompy.data.api.model.ListUberMenuResponse
import cc.femto.kommon.extensions.isSuccess
import cc.femto.kommon.extensions.retryAfterErrorResult
import retrofit2.adapter.rxjava.Result
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UberEatsApi @Inject constructor(val service: UberEatsService) {

    fun listUberMenu(): Observable<Result<ListUberMenuResponse>> = service.listUberMenu(ListUberMenuRequest())
            .retryAfterErrorResult()
            .doOnNext {
                if (it.isSuccess) {
                    val menuItems = it.response().body().menu?.get(0)?.meals
                    Timber.d("${menuItems?.size} Uber menu items: $menuItems")
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
