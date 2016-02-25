package cc.postsoft.chompy.data

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.jakewharton.byteunits.DecimalByteUnit
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    companion object {
        internal val DISK_CACHE_SIZE = DecimalByteUnit.MEGABYTES.toBytes(200).toInt()
    }

    @Provides
    @Singleton
    internal fun provideSharedPreferences(app: Application): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(app)

    @Provides
    @Singleton
    internal fun provideOkHttpClient(app: Application): OkHttpClient {
        with(OkHttpClient.Builder()) {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)

            // Install an HTTP cache in the application cache directory.
            val cacheDir = File(app.cacheDir, "http")
            val cache = Cache(cacheDir, DISK_CACHE_SIZE.toLong())
            cache(cache)

            return build()
        }
    }

    @Provides
    @Singleton
    internal fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    internal fun providePicasso(app: Application, client: OkHttpClient): Picasso = Picasso.Builder(app)
            .downloader(OkHttp3Downloader(client))
            .listener { picasso, uri, e -> Timber.e(e, "Failed to load image: %s", uri) }
            .build()
}

