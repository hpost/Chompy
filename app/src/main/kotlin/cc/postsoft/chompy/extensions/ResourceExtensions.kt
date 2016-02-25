package cc.postsoft.chompy.extensions

import cc.postsoft.chompy.App


fun dip(value: Int): Int = (value * (App.instance.resources?.displayMetrics?.density ?: 0f)).toInt()

fun dimen(resourceId: Int): Float = App.instance.resources.getDimension(resourceId)
