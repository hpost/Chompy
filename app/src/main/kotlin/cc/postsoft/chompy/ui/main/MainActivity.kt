package cc.postsoft.chompy.ui.main

import android.os.Bundle
import cc.postsoft.chompy.App
import cc.postsoft.chompy.R
import cc.postsoft.chompy.ui.common.ViewContainerActivity

class MainActivity : ViewContainerActivity() {
    override val viewId = R.layout.view_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }
}
