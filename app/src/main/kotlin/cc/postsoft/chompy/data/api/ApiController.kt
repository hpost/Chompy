package cc.postsoft.chompy.data.api

import cc.postsoft.chompy.data.api.model.ListMenuResponse
import cc.postsoft.chompy.extensions.isSuccess
import cc.postsoft.chompy.extensions.retryAfterErrorResult
import retrofit2.adapter.rxjava.Result
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiController @Inject constructor(val api: MenuApi) {

    fun listSprigMenu(): Observable<Result<ListMenuResponse>> = api.listSprigMenu()
            .retryAfterErrorResult()
            .doOnNext {
                if (it.isSuccess()) {
                    val menuItems = it.response().body().results
                    Timber.d("${menuItems?.size} Sprig menu items: $menuItems}")
                }
            }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun listCaviarMenu(): Observable<Result<ListMenuResponse>> = api.listCaviarMenu()
            .retryAfterErrorResult()
            .doOnNext {
                if (it.isSuccess()) {
                    val menuItems = it.response().body().results
                    Timber.d("${menuItems?.size} Caviar menu items: $menuItems}")
                }
            }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}
