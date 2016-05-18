package cc.femto.kommon.extensions

import android.support.annotation.StringRes
import android.util.Patterns

fun CharSequence.isEmail() = isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun CharSequence?.orDefault(defaultValue: CharSequence): CharSequence
        = if (isNullOrBlank()) defaultValue else this!!

fun CharSequence?.orDefault(@StringRes defaultValueResId: Int): CharSequence
        = if (isNullOrBlank()) string(defaultValueResId) else this!!
