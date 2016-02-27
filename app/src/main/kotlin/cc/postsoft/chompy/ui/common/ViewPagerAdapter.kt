package cc.postsoft.chompy.ui.common

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * A [PagerAdapter] that returns a view corresponding to one of the sections/tabs/pages. This provides the data
 * for the [ViewPager].
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

    /**
     * Create the page for the given position.  The adapter is responsible for adding the view to the container given
     * here, although it only must ensure this is done by the time it returns from [.finishUpdate].
     * @param container The containing View in which the page will be shown.
     * @param position  The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not need to be a View, but can be some other
     * * container of the page.
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = getItem(position)
        container.addView(view)
        return view
    }

    /**
     * Remove a page for the given position.  The adapter is responsible for removing the view from its container,
     * although it only must ensure this is done by the time it returns from [.finishUpdate].
     * @param container The containing View from which the page will be removed.
     * @param position  The page position to be removed.
     * @param object    The same object that was returned by [.instantiateItem].
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    /**
     * Determines whether a page View is associated with a specific key object as returned by [ ][.instantiateItem]. This method is required for a PagerAdapter to function properly.
     * @param view   Page View to check for association with `object`
     * @param object Object to check for association with `view`
     * @return true if `view` is associated with the key object `object`
     */
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}