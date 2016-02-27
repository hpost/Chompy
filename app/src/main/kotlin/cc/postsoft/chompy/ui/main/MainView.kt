package cc.postsoft.chompy.ui.main

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.widget.RelativeLayout
import android.widget.Toolbar
import butterknife.bindView
import cc.postsoft.chompy.App
import cc.postsoft.chompy.R
import cc.postsoft.chompy.data.AppPreferences
import cc.postsoft.chompy.extensions.ctx
import cc.postsoft.chompy.extensions.easeInVertical
import cc.postsoft.chompy.extensions.inflate
import cc.postsoft.chompy.ui.common.ViewPagerAdapter
import cc.postsoft.chompy.ui.main.MenuView.Menu.*
import com.jakewharton.rxbinding.view.preDraws
import rx.functions.Func0
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class MainView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val tabLayout: TabLayout by bindView(R.id.tab_layout)
    val viewPager: ViewPager by bindView(R.id.view_pager)

    lateinit var caviarView: MenuView
    lateinit var uberView: MenuView
    lateinit var sprigView: MenuView

    @Inject
    lateinit var preferences: AppPreferences

    private val subscriptions = CompositeSubscription()

    private var insetTop = 0

    init {
        App.component.inject(this)
    }

    //region Lifecycle
    override fun onFinishInflate() {
        super.onFinishInflate()

        // prepare entry animation
        toolbar.visibility = View.INVISIBLE

        val inflater = LayoutInflater.from(ctx)
        caviarView = inflate(R.layout.view_menu, inflater, viewPager) as MenuView
        caviarView.menu = CAVIAR
        uberView = inflate(R.layout.view_menu, inflater, viewPager) as MenuView
        uberView.menu = UBER
        sprigView = inflate(R.layout.view_menu, inflater, viewPager) as MenuView
        sprigView.menu = SPRIG

        viewPager.adapter = sectionPagerAdapter
        viewPager.offscreenPageLimit = sectionPagerAdapter.count
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // entry animation
        preDraws(Func0 { false }).limit(1).subscribe {
            toolbar.easeInVertical()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscriptions.unsubscribe()
    }

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        insetTop = insets.systemWindowInsetTop
        //        toolbar.setPadding(0, insetTop, 0, 0)
        return super.onApplyWindowInsets(insets)
    }
    //endregion Lifecycle

    val sectionPagerAdapter = object : ViewPagerAdapter() {
        override fun getCount(): Int = 3

        override fun getItem(position: Int): View {
            when (position) {
                MenuView.Menu.CAVIAR.ordinal -> return caviarView
                MenuView.Menu.UBER.ordinal -> return uberView
                MenuView.Menu.SPRIG.ordinal -> return sprigView
                else -> return View(ctx)
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                CAVIAR.ordinal -> return "Caviar"
                UBER.ordinal -> return "Uber"
                SPRIG.ordinal -> return "Sprig"
                else -> return ""
            }
        }
    }
}
