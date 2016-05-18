package cc.femto.kommon.extensions

import android.util.Patterns

object RegexPatterns {
    const val WHITESPACES = "\\s+"
    const val COMMAS = ",\\s*"
}

fun CharSequence.isEmail() = isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

inline fun CharSequence?.orDefault(defaultValue: CharSequence): CharSequence
        = if (isNullOrBlank()) defaultValue else this!!
