package cc.postsoft.chompy.ui.main

import android.os.Bundle
import cc.postsoft.android.common.ui.ViewContainerActivity
import cc.postsoft.chompy.App
import cc.postsoft.chompy.R

class MainActivity : ViewContainerActivity() {
    override val viewId = R.layout.view_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }
}
