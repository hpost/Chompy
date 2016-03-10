package cc.femto.chompy.ui.main

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.*
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import butterknife.bindView
import cc.femto.chompy.App
import cc.femto.chompy.R
import cc.femto.chompy.data.api.ApiController
import cc.femto.chompy.data.api.model.ListMenuResponse
import cc.femto.chompy.data.api.model.MenuItem
import cc.femto.common.extensions.*
import cc.femto.common.ui.MarginDecoration
import cc.femto.common.ui.widget.BetterViewAnimator
import com.jakewharton.rxbinding.support.v4.widget.refreshes
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.preDraws
import com.squareup.picasso.Picasso
import org.jetbrains.anko.dip
import retrofit2.adapter.rxjava.Result
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func0
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MenuView(context: Context, attrs: AttributeSet) : BetterViewAnimator(context, attrs) {

    enum class Menu {
        CAVIAR, UBER, SPRIG
    }

    @Inject lateinit var apiController: ApiController
    @Inject lateinit var picasso: Picasso

    val swipeRefresh: SwipeRefreshLayout by bindView(R.id.swipe_refresh)
    val menuList: RecyclerView by bindView(R.id.menu_list)
    val retryButton: Button by bindView(R.id.btn_retry)

    val columnCount = resources.getInteger(R.integer.column_count)
    val isLargeScreen = resources.getBoolean(R.bool.is_large_screen)

    private val subscriptions = CompositeSubscription()

    private lateinit var adapter: MenuItemAdapter
    private val menuSubject: PublishSubject<Any> = PublishSubject.create()
    private val TRIGGER: Any = Any()

    lateinit var menu: Menu

    init {
        App.component.inject(this)
    }

    //region Lifecycle
    override fun onFinishInflate() {
        super.onFinishInflate()

        // prepare entry animation
        menuList.visibility = View.INVISIBLE

        adapter = MenuItemAdapter(clickListener, picasso)

        val padding = dip(4)
        menuList.addItemDecoration(MarginDecoration(dip(4)))
        menuList.setPadding(padding, menuList.paddingTop + padding, padding, menuList.paddingBottom + padding)
        menuList.layoutManager = if (isLargeScreen) GridLayoutManager(ctx, columnCount) else LinearLayoutManager(ctx)
        menuList.setHasFixedSize(true)
        menuList.adapter = adapter
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // list menu
        val menuObservable: Observable<Result<out ListMenuResponse>> = menuSubject.asObservable()
                .startWith(TRIGGER)
                .doOnNext { if (adapter.itemCount == 0) swipeRefresh.isRefreshing = true }
                .flatMap {
                    when (menu) {
                        Menu.CAVIAR -> apiController.listCaviarMenu()
                        Menu.SPRIG -> apiController.listSprigMenu()
                        Menu.UBER -> apiController.listUberMenu()
                    }
                }
                .share() // multiplex
        // success
        subscriptions.add(menuObservable
                .filter { it.isSuccess }
                .map { it.response().body().results }
                .doOnNext {
                    swipeRefresh.isRefreshing = false
                    displayedChildId = if (it?.isEmpty() ?: true) R.id.empty_message else R.id.swipe_refresh
                    menuList.preDraws(Func0 { false }).limit(1).subscribe { menuList.easeInVertical() }
                }
                .subscribe { adapter.set(it) })
        // error
        subscriptions.add(menuObservable
                .filter { it.isError }
                .subscribe {
                    swipeRefresh.isRefreshing = false
                    displayedChildId = R.id.error_view
                })

        // swipe to refresh
        subscriptions.add(swipeRefresh.refreshes()
                .map { TRIGGER }
                .subscribe(menuSubject))

        // retry button
        subscriptions.add(retryButton.clicks()
                .throttleFirst(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    displayedChildId = R.id.loading_view
                    swipeRefresh.isRefreshing = true
                }
                .map { TRIGGER }
                .subscribe { menuSubject })
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscriptions.unsubscribe()
    }
    //endregion Lifecycle

    val clickListener: (MenuItem) -> Unit = {
        Timber.d("onMenuItemClick: $it")
        when (menu) {
            Menu.CAVIAR -> snack("Order from Caviar")
            Menu.UBER -> snack("Order from Uber")
            Menu.SPRIG -> snack("Order from Sprig")
        }
    }
}
