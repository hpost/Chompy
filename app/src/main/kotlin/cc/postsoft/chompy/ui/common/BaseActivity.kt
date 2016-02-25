package cc.postsoft.chompy.ui.common

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import cc.postsoft.chompy.BuildConfig
import cc.postsoft.chompy.R
import cc.postsoft.chompy.extensions.hideKeyboard

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (resources.getBoolean(R.bool.portrait_only)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onBackPressed() {
//        if (!BuildConfig.DEBUG) {
//            Crashlytics.log("onBackPressed")
//        }
        super.onBackPressed()
    }
}
