package cc.postsoft.chompy.ui.main

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.Toolbar
import butterknife.bindView
import cc.postsoft.chompy.App
import cc.postsoft.chompy.R
import cc.postsoft.chompy.data.AppPreferences
import cc.postsoft.chompy.data.api.ApiController
import cc.postsoft.chompy.extensions.easeInVertical
import cc.postsoft.chompy.extensions.isSuccess
import com.jakewharton.rxbinding.view.preDraws
import com.squareup.picasso.Picasso
import rx.functions.Func0
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import javax.inject.Inject

class MainView(context: Context, attrs: AttributeSet) : CoordinatorLayout(context, attrs) {

    val toolbar: Toolbar by bindView(R.id.toolbar)

    val fab: FloatingActionButton by bindView(R.id.fab)

    @Inject
    lateinit var preferences: AppPreferences

    @Inject
    lateinit var apiController: ApiController

    @Inject
    lateinit var picasso: Picasso

    private val subscriptions = CompositeSubscription()

    private var insetTop = 0

    init {
        App.component.inject(this)
    }

    //region Lifecycle
    override fun onFinishInflate() {
        super.onFinishInflate()

        //        val inflater = LayoutInflater.from(context)
        //        collectionsView = inflater.inflate(R.layout.view_collections, viewPager, false) as CollectionsView

        // prepare entry animation
        toolbar.visibility = View.INVISIBLE
        fab.visibility = View.INVISIBLE
        fab.hide()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // FAB
        //        subscriptions.add(fab.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
        //            // TODO
        //        })

        // entry animation
        preDraws(Func0 { false }).limit(1).subscribe {
            toolbar.easeInVertical() { startDelay = 200 }
        }
        postDelayed({ fab.show() }, 1000)

        subscriptions.add(apiController
                .listSprigMenu()
                .subscribe({ result -> if (result.isSuccess()) Timber.d("succss with Sprig!") }) {
                    Timber.e(it, "error while fetching Sprig menu")
                })
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscriptions.unsubscribe()
    }

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        insetTop = insets.systemWindowInsetTop
        //        banquetBanner.setPadding(0, insetTop, 0, 0)
        return super.onApplyWindowInsets(insets)
    }
    //endregion Lifecycle
}
