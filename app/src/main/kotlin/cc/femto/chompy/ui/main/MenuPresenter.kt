package cc.femto.chompy.ui.main

import cc.femto.chompy.R
import cc.femto.chompy.data.api.MenuApi
import cc.femto.chompy.data.api.UberEatsApi
import cc.femto.chompy.data.api.model.ListMenuResponse
import cc.femto.chompy.data.api.model.ListUberMenuResponse
import cc.femto.chompy.data.api.model.Menu
import cc.femto.chompy.data.api.model.MenuItem
import cc.femto.kommon.extensions.isSuccess
import cc.femto.kommon.extensions.string
import cc.femto.kommon.mvp.MvpContentPresenter
import retrofit2.adapter.rxjava.Result
import rx.Observable
import rx.lang.kotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

class MenuPresenter @Inject constructor(val menuApi: MenuApi,
                                        val uberEatsApi: UberEatsApi)
: MvpContentPresenter<List<MenuItem>, MenuView>() {

    fun loadMenu(menu: Menu) =
            when (menu) {
                Menu.CAVIAR -> loadMenuFromMagic(menuApi.listCaviarMenu())
                Menu.SPRIG -> loadMenuFromMagic(menuApi.listSprigMenu())
                Menu.UBER -> loadMenuFromUber(uberEatsApi.listUberMenu())
            }

    private fun loadMenuFromMagic(source: Observable<Result<ListMenuResponse>>) {
        deferLoading()
        val result = source
                .doOnCompleted { onLoadingFinished() }
                .publish().autoConnect() // multiplex
        // success
        subscriptions += result.filter { it.isSuccess }
                .map { it.response().body().results ?: emptyList<MenuItem>() }
                .subscribe {
                    view?.bindTo(it)
                    view?.showContent()
                }
        // error
        subscriptions += result.filter { !it.isSuccess }.subscribe {
            view?.showError(string(R.string.error_unable_to_load_msg))
            Timber.e("error while fetching menu")
        }
    }

    private fun loadMenuFromUber(source: Observable<Result<ListUberMenuResponse>>) {
        deferLoading()
        val result = source
                .doOnCompleted { onLoadingFinished() }
                .publish().autoConnect() // multiplex
        // success
        subscriptions += result.filter { it.isSuccess }
                .map { it.response().body().results ?: emptyList<MenuItem>() }
                .subscribe {
                    view?.bindTo(it)
                    view?.showContent()
                }
        // error
        subscriptions += result.filter { !it.isSuccess }.subscribe {
            view?.showError(string(R.string.error_unable_to_load_msg))
            Timber.e("error while fetching menu")
        }
    }
}