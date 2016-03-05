package cc.postsoft.android.extensions

import android.util.Patterns

object Regex {
    const val WHITESPACES = "\\s+"
    const val COMMAS = ",\\s*"
}

fun CharSequence.isBlank() = !isNotBlank()

fun CharSequence.isEmail() = isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

