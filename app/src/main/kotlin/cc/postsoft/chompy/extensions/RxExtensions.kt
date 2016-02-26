package cc.postsoft.chompy.extensions

import retrofit2.adapter.rxjava.Result
import rx.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Resubscribe to source up to `attempts` times with exponential backoff
 * @param attempts Maximum number of retries
 * @return Observable with retry logic
 */
fun <T> Observable<T>.retryAfterTimeout(attempts: Int = 3): Observable<T> = retryWhen { error ->
    error.zipWith(Observable.range(0, attempts + 1), { t, i -> t to i })
            .doOnNext {
                if (it.second < attempts)
                    Timber.d("Retrying in ${ld(it.second)}s (${(it.first as Throwable).message})")
                else
                    Timber.e("Failing after $attempts attempts (${(it.first as Throwable).message})")
            }
            .flatMap {
                if (it.second === attempts)
                    Observable.error(it.first as Throwable)
                else
                    Observable.timer(ld(it.second).toLong(), TimeUnit.SECONDS)
            }
}

/**
 * Resubscribe to Retrofit request up to `attempts` times with exponential backoff.
 * Converts the erroneous result to an exception which can be handled by RxJava's retry operators.
 * Wraps the final exception in a result object.
 * @param attempts Maximum number of retries
 * @return Observable with retry logic that wraps the error in a Retrofit [Result]
 */
fun <T> Observable<Result<T>>.retryAfterErrorResult(attempts: Int = 3): Observable<Result<T>> =
        doOnNext { if (!it.isSuccess()) throw RuntimeException(it.error()) }
                .retryAfterTimeout(attempts)
                .onErrorReturn { throwable -> Result.error(throwable) }

////region Error Handling
//var snackOnError = { throwable, view, description ->
//    Snack.error(view, view.getResources().getString(R.string.error_something_wrong_msg)).show()
//    Timber.e("%s: %s", description, throwable)
//}
//
//var logOnError = { throwable ->
//    if (throwable is ApiException) {
//        val error = (throwable as ApiException).getError()
//        Timber.e(throwable, App.getInstance().getResources().getString(R.string.error_msg, error.getName()))
//    } else {
//        Timber.e(throwable, App.getInstance().getResources().getString(R.string.error_something_wrong_msg))
//    }
//}
////endregion Error Handling