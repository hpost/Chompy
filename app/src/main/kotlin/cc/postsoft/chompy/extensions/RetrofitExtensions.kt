package cc.postsoft.chompy.extensions

import retrofit2.adapter.rxjava.Result

inline fun <T> Result<T>.isSuccess(): Boolean = !isError && response().isSuccess
