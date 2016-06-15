package cc.femto.chompy.data.api

import android.os.Build
import cc.femto.chompy.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Named
import javax.inject.Singleton


@Module
class ApiModule {

    companion object {

        val BASE_URL = "https://ubereats-city.appspot.com/_api/"

        /**
         * `cc.postsoft.chompy; 0.1; 1; Sony D6503; 4.4.2; en_US`
         */
        val USER_AGENT = "${BuildConfig.APPLICATION_ID}; ${BuildConfig.VERSION_NAME}; " +
                "${BuildConfig.VERSION_CODE}; ${Build.MANUFACTURER} ${Build.MODEL}; " +
                "${Build.VERSION.RELEASE}; ${Locale.getDefault().toString()}"
    }

    @Provides
    @Singleton
    @Named("Api")
    internal fun provideApiClient(client: OkHttpClient): OkHttpClient {
        val apiClient = client.newBuilder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            apiClient.networkInterceptors().add(loggingInterceptor)
            apiClient.networkInterceptors().add(Interceptor { chain ->
                val original: Request? = chain?.request()
                val request: Request? = original?.newBuilder()
                        ?.header("User-Agent", USER_AGENT)
                        ?.method(original.method(), original.body())
                        ?.build()
                return@Interceptor chain?.proceed(request)
            })
        }
        return apiClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(@Named("Api") client: OkHttpClient, moshi: Moshi): Retrofit =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()

    @Provides
    @Singleton
    internal fun provideUberEatsApi(retrofit: Retrofit): UberEatsService = retrofit.create(UberEatsService::class.java)
}
