package cc.postsoft.android.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Outline
import android.graphics.Rect
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.*
import android.widget.TextView

fun inflate(layoutResId: Int, inflater: LayoutInflater?, container: ViewGroup?): View? =
        inflater?.inflate(layoutResId, container, false)

val View.ctx: Context
    get() = context

val View.activity: Activity
    get() = context as Activity

val View.window: Window
    get() = activity.window

var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)

fun View.hideKeyboard() {
    activity.hideKeyboard()
}

fun View.snack(msg: CharSequence? = null, @StringRes msgResId: Int? = null,
               @ColorRes colorResId: Int? = null, vararg args: String?,
               build: (Snackbar.() -> Unit)? = null) {
    val snackbar =
            if (msg != null) {
                Snackbar.make(this, msg, Snackbar.LENGTH_SHORT)
            } else if (msgResId != null) {
                Snackbar.make(this, resources.getString(msgResId, args), Snackbar.LENGTH_SHORT)
            } else {
                throw IllegalArgumentException("expecting CharSequence or string resource ID")
            }
    colorResId?.let { snackbar.view.setBackgroundColor(color(colorResId)) }
    build?.let { snackbar.build() }
    snackbar.show()
}

fun View.intersectsWith(other: View): Boolean {
    val view1Loc = intArrayOf(0, 0)
    getLocationOnScreen(view1Loc)
    val view1Rect = Rect(view1Loc[0],
            view1Loc[1],
            view1Loc[0] + width,
            view1Loc[1] + height)
    val view2Loc = intArrayOf(0, 0)
    other.getLocationOnScreen(view2Loc)
    val view2Rect = Rect(view2Loc[0],
            view2Loc[1],
            view2Loc[0] + other.width,
            view2Loc[1] + other.height)
    return view1Rect.intersect(view2Rect)
}

/** Used for views shown in BottomSheetLayout */
class ShadowOutline(val width: Int, val height: Int) : ViewOutlineProvider() {
    override fun getOutline(view: View?, outline: Outline?) {
        outline?.setRect(0, 0, width, height)
    }
}
