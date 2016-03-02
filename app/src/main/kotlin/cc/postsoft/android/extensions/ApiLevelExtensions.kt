package cc.postsoft.android.extensions

import android.os.Build

inline fun api23(code: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        code()
    }
}