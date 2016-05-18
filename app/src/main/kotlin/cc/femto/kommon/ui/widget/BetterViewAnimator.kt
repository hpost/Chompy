package cc.femto.kommon.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ViewAnimator

open class BetterViewAnimator(context: Context, attrs: AttributeSet) : ViewAnimator(context, attrs) {

    var displayedChildId: Int
        get() = getChildAt(displayedChild).id
        set(id) {
            if (displayedChildId == id) {
                return
            }
            for (i in 0..childCount) {
                if (getChildAt(i).id == id) {
                    displayedChild = i
                    return
                }
            }
            val name = resources.getResourceEntryName(id)
            throw IllegalArgumentException("No view with ID " + name)
        }
}
