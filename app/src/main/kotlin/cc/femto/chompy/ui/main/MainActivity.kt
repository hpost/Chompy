package cc.femto.chompy.ui.main

import android.os.Bundle
import cc.femto.chompy.App
import cc.femto.chompy.R
import cc.femto.common.ui.activity.ViewContainerActivity

class MainActivity : ViewContainerActivity() {
    override val viewId = R.layout.view_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }
}
