package cc.postsoft.android.extensions

import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.util.TypedValue
import cc.postsoft.chompy.App


fun dip(value: Int): Int = (value * (App.instance.resources?.displayMetrics?.density ?: 0f)).toInt()

fun dimen(@DimenRes resourceId: Int): Float = App.instance.resources.getDimension(resourceId)

fun color(@ColorRes resourceId: Int): Int = App.instance.resources.getColor(resourceId, App.instance.theme)

val toolbarHeight: Int = -1
    get() {
        if (field < 0) {
            val value = TypedValue()
            App.instance.theme.resolveAttribute(android.R.attr.actionBarSize, value, true)
            field = TypedValue.complexToDimensionPixelSize(value.data, App.instance
                    .resources.displayMetrics)
        }
        return field
    }
