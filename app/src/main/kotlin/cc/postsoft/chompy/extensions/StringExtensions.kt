package cc.postsoft.chompy.extensions

fun CharSequence.isBlank() = toString().trim().length == 0
