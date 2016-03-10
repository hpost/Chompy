package cc.femto.common.ui

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * A [PagerAdapter] that returns a view corresponding to one of the sections/tabs/pages.
 * This provides the data for the [ViewPager].
 */
abstract class ViewPagerAdapter() : PagerAdapter() {

    /**
     * Get view corresponding to a specific position. This will be used to populate the contents of the [ ].
     * @param position Position to fetch view for.
     * @return View for specified position.
     */
    abstract fun getItem(position: Int): View

    /**
     * Get number of pages the [ViewPager] should render.
     * @return Number of views to be rendered as pages.
     */
    abstract override fun getCount(): Int

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = getItem(position)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}