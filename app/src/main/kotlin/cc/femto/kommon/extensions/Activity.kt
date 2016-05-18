package cc.femto.kommon.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus
    if (view != null) {
        view.clearFocus()
        inputManager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}
