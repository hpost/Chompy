package cc.postsoft.chompy

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class App : Application() {

    companion object {
        // static annotation allows access from Java code
        @JvmStatic lateinit var instance: Application
        @JvmStatic lateinit var component: AppComponent
    }

//    @Inject
//    lateinit var preferences : AppPreferences

    override fun onCreate() {
        super.onCreate()
        instance = this

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        component.inject(this)

        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            //        } else {
            //            Timber.plant(CrashlyticsTree())
        }

    }
}