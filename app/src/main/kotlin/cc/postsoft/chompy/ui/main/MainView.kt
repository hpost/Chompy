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
import cc.postsoft.android.common.ui.ViewPagerAdapter
import cc.postsoft.android.extensions.color
import cc.postsoft.android.extensions.colorFade
import cc.postsoft.android.extensions.ctx
import cc.postsoft.android.extensions.window
import cc.postsoft.chompy.App
import cc.postsoft.chompy.R
import cc.postsoft.chompy.data.AppPreferences
import cc.postsoft.chompy.ui.main.MenuView.Menu.*
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

        val inflater = LayoutInflater.from(ctx)
        caviarView = cc.postsoft.android.extensions.inflate(R.layout.view_menu, inflater, viewPager) as MenuView
        caviarView.menu = CAVIAR
        uberView = cc.postsoft.android.extensions.inflate(R.layout.view_menu, inflater, viewPager) as MenuView
        uberView.menu = UBER
        sprigView = cc.postsoft.android.extensions.inflate(R.layout.view_menu, inflater, viewPager) as MenuView
        sprigView.menu = SPRIG

        viewPager.adapter = sectionPagerAdapter
        viewPager.offscreenPageLimit = sectionPagerAdapter.count
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                setColor(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })
        tabLayout.setupWithViewPager(viewPager)

        setColor(viewPager.currentItem, animate = false)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
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

    private fun setColor(position: Int, animate: Boolean = true) {
        val color: Int = when (position) {
            CAVIAR.ordinal -> color(R.color.orange)
            UBER.ordinal -> color(R.color.grey)
            SPRIG.ordinal -> color(R.color.green)
            else -> color(R.color.colorPrimary)
        }
        val colorDark: Int = when (position) {
            CAVIAR.ordinal -> color(R.color.orangeDark)
            UBER.ordinal -> color(R.color.greyDark)
            SPRIG.ordinal -> color(R.color.greenDark)
            else -> color(R.color.colorPrimaryDark)
        }
        if (animate) {
            toolbar.colorFade(color)
        } else {
            toolbar.setBackgroundColor(color)
        }
        window.statusBarColor = colorDark
    }

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
