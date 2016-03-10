package cc.femto.chompy.data

import android.content.SharedPreferences

import com.f2prateek.rx.preferences.Preference
import com.f2prateek.rx.preferences.RxSharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(sharedPreferences: SharedPreferences) {

    val city: Preference<String>

    init {
        val rxPreferences = RxSharedPreferences.create(sharedPreferences)
        city = rxPreferences.getString("city", null)
    }
}
