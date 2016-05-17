package cc.femto.chompy.data.api

import cc.femto.chompy.data.api.model.ListMenuResponse
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
class MenuApi @Inject constructor(val service: MenuService) {

    fun listSprigMenu(): Observable<Result<ListMenuResponse>> = service.listSprigMenu()
            .retryAfterErrorResult()
            .doOnNext {
                if (it.isSuccess) {
                    val menuItems = it.response().body().results
                    Timber.d("${menuItems?.size} Sprig menu items: $menuItems}")
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun listCaviarMenu(): Observable<Result<ListMenuResponse>> = service.listCaviarMenu()
            .retryAfterErrorResult()
            .doOnNext {
                if (it.isSuccess) {
                    val menuItems = it.response().body().results
                    Timber.d("${menuItems?.size} Caviar menu items: $menuItems}")
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
