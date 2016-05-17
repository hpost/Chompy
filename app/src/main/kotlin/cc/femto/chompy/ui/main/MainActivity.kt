package cc.femto.chompy.ui.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import butterknife.bindView
import cc.femto.chompy.App
import cc.femto.chompy.R
import cc.femto.chompy.data.api.model.Menu
import cc.femto.kommon.extensions.color
import cc.femto.kommon.extensions.colorFade
import cc.femto.kommon.extensions.inflate
import cc.femto.kommon.ui.ViewPagerAdapter
import cc.femto.kommon.ui.activity.BaseActivity
import org.jetbrains.anko.ctx

class MainActivity : BaseActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val tabLayout: TabLayout by bindView(R.id.tab_layout)
    val viewPager: ViewPager by bindView(R.id.view_pager)

    lateinit var caviarLayout: MenuLayout
    lateinit var uberLayout: MenuLayout
    lateinit var sprigLayout: MenuLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        App.component.inject(this)

        val inflater = LayoutInflater.from(ctx)
        caviarLayout = viewPager.inflate(R.layout.menu_layout, inflater) as MenuLayout
        caviarLayout.menu = Menu.CAVIAR
        uberLayout = viewPager.inflate(R.layout.menu_layout, inflater) as MenuLayout
        uberLayout.menu = Menu.UBER
        sprigLayout = viewPager.inflate(R.layout.menu_layout, inflater) as MenuLayout
        sprigLayout.menu = Menu.SPRIG

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

    private fun setColor(position: Int, animate: Boolean = true) {
        val color: Int = when (position) {
            Menu.CAVIAR.ordinal -> color(R.color.orange)
            Menu.UBER.ordinal -> color(R.color.grey)
            Menu.SPRIG.ordinal -> color(R.color.green)
            else -> color(R.color.colorPrimary)
        }
        val colorDark: Int = when (position) {
            Menu.CAVIAR.ordinal -> color(R.color.orangeDark)
            Menu.UBER.ordinal -> color(R.color.greyDark)
            Menu.SPRIG.ordinal -> color(R.color.greenDark)
            else -> color(R.color.colorPrimaryDark)
        }
        if (animate) {
            toolbar.colorFade(to = color)
        } else {
            toolbar.setBackgroundColor(color)
        }
        window.statusBarColor = colorDark
    }

    val sectionPagerAdapter = object : ViewPagerAdapter() {
        override fun getCount(): Int = 3

        override fun getItem(position: Int): View {
            when (position) {
                Menu.CAVIAR.ordinal -> return caviarLayout
                Menu.UBER.ordinal -> return uberLayout
                Menu.SPRIG.ordinal -> return sprigLayout
                else -> return View(ctx)
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                Menu.CAVIAR.ordinal -> return "Caviar"
                Menu.UBER.ordinal -> return "Uber"
                Menu.SPRIG.ordinal -> return "Sprig"
                else -> return ""
            }
        }
    }
}
