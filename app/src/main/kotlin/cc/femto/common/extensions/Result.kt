package cc.femto.common.extensions

import retrofit2.adapter.rxjava.Result

val <T> Result<T>.isSuccess: Boolean
    get() = !isError && response().isSuccess
