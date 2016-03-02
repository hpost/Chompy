package cc.postsoft.chompy.extensions

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView

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

fun inflate(layoutResId: Int, inflater: LayoutInflater?, container: ViewGroup?): View? =
        inflater?.inflate(layoutResId, container, false)

inline fun View.snack(msg: CharSequence) = Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
