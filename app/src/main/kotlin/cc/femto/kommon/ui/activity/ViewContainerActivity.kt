package cc.femto.kommon.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.ViewGroup

abstract class ViewContainerActivity : BaseActivity() {

    protected var contentView: ViewGroup? = null
        private set

    /**
     * Called in ViewContainerActivity#onCreate.
     * @return The resource ID of the view that should be embedded in this Activity
     */
    abstract val viewId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contentView = layoutInflater.inflate(viewId, null, false) as ViewGroup
        setContentView(contentView)
    }

    override fun onNewIntent(intent: Intent) {
        if (contentView is OnNewIntentListener) {
            (contentView as OnNewIntentListener).onNewIntent(intent)
        }
        super.onNewIntent(intent)
    }

    override fun onBackPressed() {
        var consumed = false
        if (contentView is OnBackPressedListener) {
            consumed = (contentView as OnBackPressedListener).onBackPressed()
        }
        if (!consumed) {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (contentView is OnActivityResultListener) {
            (contentView as OnActivityResultListener).onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (contentView is ActivityCompat.OnRequestPermissionsResultCallback) {
            (contentView as ActivityCompat.OnRequestPermissionsResultCallback).onRequestPermissionsResult(requestCode,
                    permissions, grantResults)
        }
    }

    interface OnBackPressedListener {
        fun onBackPressed(): Boolean
    }

    interface OnNewIntentListener {
        fun onNewIntent(intent: Intent)
    }

    interface OnActivityResultListener {
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }
}
