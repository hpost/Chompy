package cc.postsoft.chompy

import cc.postsoft.chompy.data.DataModule
import cc.postsoft.chompy.data.api.ApiModule
import cc.postsoft.chompy.ui.main.MainActivity
import cc.postsoft.chompy.ui.main.MainView
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, ApiModule::class))
interface AppComponent {
    fun inject(application: App)
    fun inject(activity: MainActivity)
    fun inject(activity: MainView)
}

