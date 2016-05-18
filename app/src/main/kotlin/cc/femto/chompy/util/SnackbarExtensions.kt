package cc.femto.chompy.util

import android.support.design.widget.Snackbar
import android.view.View
import cc.femto.chompy.R
import cc.femto.kommon.extensions.snack

fun View.snackError(msg: CharSequence, duration: Int = Snackbar.LENGTH_SHORT, build: (Snackbar.() -> Unit)? = null)
        = snack(msg, R.color.error, duration, build)

fun View.snackSuccess(msg: CharSequence, duration: Int = Snackbar.LENGTH_SHORT, build: (Snackbar.() -> Unit)? = null)
        = snack(msg, R.color.success, duration, build)
