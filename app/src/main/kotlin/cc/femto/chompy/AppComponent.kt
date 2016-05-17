package cc.femto.chompy

import cc.femto.chompy.data.DataModule
import cc.femto.chompy.data.api.ApiModule
import cc.femto.chompy.ui.main.MainActivity
import cc.femto.chompy.ui.main.MenuLayout
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, ApiModule::class))
interface AppComponent {
    fun inject(application: App)
    fun inject(activity: MainActivity)
    fun inject(activity: MenuLayout)
}

