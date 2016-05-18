package cc.femto.chompy.ui.main

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import butterknife.bindView
import cc.femto.chompy.App
import cc.femto.chompy.R
import cc.femto.chompy.data.api.model.Menu
import cc.femto.chompy.data.api.model.MenuItem
import cc.femto.kommon.extensions.ctx
import cc.femto.kommon.extensions.easeInVertical
import cc.femto.kommon.extensions.preDraw
import cc.femto.kommon.extensions.snack
import cc.femto.kommon.mvp.MvpContentLayout
import cc.femto.kommon.ui.recyclerview.MarginDecoration
import com.jakewharton.rxbinding.support.v4.widget.refreshes
import com.squareup.picasso.Picasso
import org.jetbrains.anko.dip
import rx.lang.kotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

class MenuLayout(context: Context, attrs: AttributeSet)
: MvpContentLayout<List<MenuItem>, MenuView, MenuPresenter>(context, attrs), MenuView {

    @Inject override lateinit var presenter: MenuPresenter
    @Inject lateinit var picasso: Picasso

    val swipeRefresh: SwipeRefreshLayout by bindView(R.id.swipe_refresh)
    val contentView: RecyclerView by bindView(R.id.content_view)

    val columnCount = resources.getInteger(R.integer.column_count)
    val isLargeScreen = resources.getBoolean(R.bool.is_large_screen)

    private lateinit var adapter: MenuItemAdapter
    lateinit var menu: Menu

    init {
        App.component.inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        adapter = MenuItemAdapter(clickListener, picasso)

        val padding = dip(4)
        contentView.addItemDecoration(MarginDecoration(dip(4)))
        contentView.setPadding(padding, contentView.paddingTop + padding, padding, contentView.paddingBottom + padding)
        contentView.layoutManager = if (isLargeScreen) GridLayoutManager(ctx, columnCount) else LinearLayoutManager(ctx)
        contentView.setHasFixedSize(true)
        contentView.adapter = adapter

        // prepare entry animation
        contentView.visibility = View.INVISIBLE
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // swipe to refresh
        subscriptions += swipeRefresh.refreshes()
                .subscribe { loadData(true) }

        loadData(false)
    }

    override fun loadData(pullToRefresh: Boolean) {
        presenter.loadMenu(menu)
    }

    override fun bindTo(data: List<MenuItem>) = adapter.bindTo(data)

    override fun showContent() {
        swipeRefresh.isRefreshing = false
        viewAnimator.displayedChildId = R.id.swipe_refresh
        preDraw { contentView.easeInVertical() }
    }

    val clickListener: (MenuItem) -> Unit = {
        Timber.d("onMenuItemClick: $it")
        when (menu) {
            Menu.CAVIAR -> snack("Order from Caviar")
            Menu.UBER -> snack("Order from Uber")
            Menu.SPRIG -> snack("Order from Sprig")
        }
    }
}
