package cc.postsoft.android.common.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cc.postsoft.android.extensions.hideKeyboard
import cc.postsoft.chompy.R

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