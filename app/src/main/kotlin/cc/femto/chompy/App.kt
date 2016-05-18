package cc.femto.chompy

import android.app.Application
import cc.femto.kommon.Kommon
import timber.log.Timber

class App : Application() {

    companion object {
        // static annotation allows access from Java code
        @JvmStatic lateinit var instance: Application
        @JvmStatic lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        component.inject(this)

        Kommon.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}