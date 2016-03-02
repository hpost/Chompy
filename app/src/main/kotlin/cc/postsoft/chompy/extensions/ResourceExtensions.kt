package cc.postsoft.chompy.extensions

import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import cc.postsoft.chompy.App


fun dip(value: Int): Int = (value * (App.instance.resources?.displayMetrics?.density ?: 0f)).toInt()

fun dimen(@DimenRes resourceId: Int): Float = App.instance.resources.getDimension(resourceId)

fun color(@ColorRes resourceId: Int): Int = App.instance.resources.getColor(resourceId, App.instance.theme)
