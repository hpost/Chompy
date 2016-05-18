package cc.femto.kommon.util

import okhttp3.Interceptor
import java.io.IOException

/**
 * An [Interceptor] for dynamically changing request hosts
 */
class HostSelectionInterceptor : Interceptor {
    @Volatile var host: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        val host = this.host
        if (host != null) {
            val newUrl = request.url().newBuilder().host(host).build()
            request = request.newBuilder().url(newUrl).build()
        }
        return chain.proceed(request)
    }
}