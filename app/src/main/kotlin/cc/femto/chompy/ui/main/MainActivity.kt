package cc.femto.chompy.ui.main

import android.os.Bundle
import android.support.v7.widget.Toolbar
import butterknife.bindView
import cc.femto.chompy.App
import cc.femto.chompy.R
import cc.femto.kommon.ui.activity.BaseActivity

class MainActivity : BaseActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        App.component.inject(this)
    }

}
