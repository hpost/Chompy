package cc.femto.chompy


import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return app
    }

}

