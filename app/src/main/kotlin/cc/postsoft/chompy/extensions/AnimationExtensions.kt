package cc.postsoft.chompy.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.*
import cc.postsoft.chompy.R


object AnimationConstants {

    const val LICKETY_SPLIT = 150L;

    const val SHORT = 250L;

    const val MEDIUM = 400L;

    const val LONG = 600L;

    val TRANSLATION = dip(75)

    val TRANSLATION_SMALL = dip(16)

    val ELEVATION = dimen(R.dimen.elevation_toolbar)

    val ACCELERATE = AccelerateInterpolator()

    val DECELERATE = DecelerateInterpolator()

    val LINEAR_OUT_SLOW_IN = LinearOutSlowInInterpolator()

    val FAST_OUT_SLOW_IN = FastOutSlowInInterpolator()

    val FAST_OUT_LINEAR_IN = FastOutLinearInInterpolator()

    val ACCELERATE_DECELERATE = AccelerateDecelerateInterpolator()

    val OVERSHOOT = OvershootInterpolator()

    val CYCLE_3 = CycleInterpolator(3f)
}

fun View.slideExit() {
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

fun View.slideEnter() {
    if (translationY < 0f) animate().translationY(0f)
}

fun View.easeInVertical(vararg views: View, build: ViewPropertyAnimator.() -> Unit = { }) {
    var offset = AnimationConstants.TRANSLATION_SMALL.toFloat()
    for (view: View? in views) {
        if (view == null || view.visibility == View.VISIBLE) {
            continue;
        }
        view.alpha = 0.8f
        view.translationY = offset
        val anim = view.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(AnimationConstants.LONG)
                .setInterpolator(AnimationConstants.FAST_OUT_SLOW_IN)
                //                .withLayer() // TODO measure!
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        view.visibility = View.VISIBLE
                    }
                })
        anim.build()
        anim.start()
        offset *= 1.5f
    }
}

fun View.easeInVertical(build: ViewPropertyAnimator.() -> Unit = { }) {
    if (visibility != View.VISIBLE) {
        alpha = 0.8f
        translationY = AnimationConstants.TRANSLATION_SMALL.toFloat()
        val anim = animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(AnimationConstants.LONG)
                .setInterpolator(AnimationConstants.FAST_OUT_SLOW_IN)
                //                .withLayer() // TODO measure!
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        visibility = View.VISIBLE
                    }
                })
        anim.build()
        anim.start()
    }
}
