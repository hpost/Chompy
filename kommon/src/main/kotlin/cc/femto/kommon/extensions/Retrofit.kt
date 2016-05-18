package cc.femto.kommon.extensions

val <T> retrofit2.adapter.rxjava.Result<T>.isSuccess: Boolean
    get() = !isError && response().isSuccessful
