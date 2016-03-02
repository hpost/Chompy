package cc.postsoft.android.extensions

import retrofit2.adapter.rxjava.Result

inline fun <T> Result<T>.isSuccess(): Boolean = !isError && response().isSuccess
