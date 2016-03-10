package cc.femto.common.extensions

import android.R
import android.support.annotation.*
import android.util.TypedValue
import cc.femto.chompy.App

fun dip(value: Int): Int = (value * (App.instance.resources.displayMetrics.density)).toInt()

fun bool(@BoolRes resourceId: Int): Boolean = App.instance.resources.getBoolean(resourceId)

fun dimen(@DimenRes resourceId: Int): Float = App.instance.resources.getDimension(resourceId)

fun color(@ColorRes resourceId: Int): Int = App.instance.resources.getColor(resourceId, App.instance.theme)

fun string(@StringRes resourceId: Int): String = App.instance.resources.getString(resourceId)
fun string(@StringRes resourceId: Int, vararg args: String): String = App.instance.resources.getString(resourceId, args)

val toolbarHeight: Int = -1
    get() {
        if (field < 0) {
            val value = TypedValue()
            App.instance.theme.resolveAttribute(R.attr.actionBarSize, value, true)
            field = TypedValue.complexToDimensionPixelSize(value.data, App.instance
                    .resources.displayMetrics)
        }
        return field
    }
