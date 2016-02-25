package cc.postsoft.chompy.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

fun Activity.hideKeyboard() {
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus
    if (view != null) {
        view.clearFocus()
        inputManager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}
