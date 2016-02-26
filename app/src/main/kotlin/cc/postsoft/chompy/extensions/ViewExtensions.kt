package cc.postsoft.chompy.extensions

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

fun View.inflate(layoutResId: Int, inflater: LayoutInflater?, container: ViewGroup?): View? =
        inflater?.inflate(layoutResId, container, false)
