package cc.postsoft.chompy.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView

val View.ctx: Context
    get() = context

val View.activity: Activity
    get() = context as Activity

var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)

fun View.hideKeyboard() {
    activity.hideKeyboard()
}
