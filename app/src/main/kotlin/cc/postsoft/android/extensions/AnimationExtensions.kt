package cc.postsoft.android.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorRes
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewPropertyAnimator
import android.view.animation.*
import cc.postsoft.chompy.R


object AnimationConstants {
    const val LICKETY_SPLIT = 150L
    const val SHORT = 250L
    const val MEDIUM = 400L
    const val LONG = 600L

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

fun View.fade(initAlpha: Float? = null, alpha: Float = 1f, startDelay: Long = 0,
              build: (ViewPropertyAnimator.() -> Unit)? = null) {
    initAlpha?.let { this.alpha = initAlpha }
    val anim = animate()
            .alpha(alpha)
            .setStartDelay(startDelay)
            .setDuration(AnimationConstants.SHORT)
            .setInterpolator(AnimationConstants.LINEAR_OUT_SLOW_IN)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (alpha == 0f) visibility = View.INVISIBLE
                }
            })
    build?.let { anim.build() }
    anim.start()
}

fun View.translate(initX: Int? = null, initY: Int? = null, translationX: Int = 0, translationY: Int = 0,
                   initAlpha: Float? = null, alpha: Float = 1f, startDelay: Long = 0,
                   build: (ViewPropertyAnimator.() -> Unit)? = null) {
    initX?.let { this.translationX = initX.toFloat() }
    initY?.let { this.translationY = initY.toFloat() }
    initAlpha?.let { this.alpha = initAlpha }
    val anim = animate()
            .alpha(alpha)
            .translationX(translationX.toFloat())
            .translationY(translationY.toFloat())
            .setStartDelay(startDelay)
            .setDuration(AnimationConstants.MEDIUM)
            .setInterpolator(AnimationConstants.FAST_OUT_SLOW_IN)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (alpha == 0f) visibility = View.INVISIBLE
                }
            })
    build?.let { anim.build() }
    anim.start()
}

fun easeInVertical(startDelay: Long = 0, vararg views: View, build: (ViewPropertyAnimator.() -> Unit)? = null) {
    var offset = AnimationConstants.TRANSLATION_SMALL.toFloat()
    for (view: View? in views) {
        if (view == null || view.visibility == View.VISIBLE) {
            continue
        }
        view.alpha = 0.8f
        view.translationY = offset
        val anim = view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(startDelay)
                .setDuration(AnimationConstants.LONG)
                .setInterpolator(AnimationConstants.FAST_OUT_SLOW_IN)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        view.visibility = View.VISIBLE
                    }
                })
        build?.let { anim.build() }
        anim.start()
        offset *= 1.5f
    }
}

fun View.easeInVertical(startDelay: Long = 0, build: (ViewPropertyAnimator.() -> Unit)? = null)
        = easeInVertical(startDelay, this) { build?.let { build() } }

fun easeOutVertical(startDelay: Long = 0, vararg views: View, build: (ViewPropertyAnimator.() -> Unit)? = null) {
    var offset = AnimationConstants.TRANSLATION_SMALL.toFloat()
    for (view: View? in views) {
        if (view == null || view.visibility != View.VISIBLE) {
            continue
        }
        val anim = view.animate()
                .alpha(0f)
                .translationY(-offset)
                .setStartDelay(startDelay)
                .setDuration(AnimationConstants.LONG)
                .setInterpolator(AnimationConstants.FAST_OUT_SLOW_IN)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.INVISIBLE
                    }
                })
        build?.let { anim.build() }
        anim.start()
        offset *= 1.5f
    }
}

fun View.easeOutVertical(startDelay: Long = 0, build: (ViewPropertyAnimator.() -> Unit)? = null)
        = easeOutVertical(startDelay, this) { build?.let { build() } }

fun View.reveal(show: Boolean = true, centerX: Int? = null, centerY: Int? = null,
                build: (Animator.() -> Unit)? = null) {
    if (!isAttachedToWindow) {
        return
    }
    val cx = (left + right) / 2
    val cy = (top + bottom) / 2
    val radius = Math.hypot(width.toDouble(), height.toDouble()).toFloat()

    val anim = ViewAnimationUtils.createCircularReveal(this,
            centerX ?: cx, centerY ?: cy,
            if (show) 0f else radius, if (show) radius else 0f
    )
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
            if (show) {
                visibility = View.VISIBLE
            }
        }

        override fun onAnimationEnd(animation: Animator?) {
            if (!show) {
                visibility = View.INVISIBLE
            }
        }
    })
    build?.let { anim.build() }
    anim.start()
}

fun View.hide(centerX: Int? = null, centerY: Int? = null, build: (Animator.() -> Unit)? = null)
        = reveal(false, centerX, centerY, build)

fun colorFade(@ColorRes from: Int, @ColorRes to: Int, update: (Int) -> Unit) {
    val anim = ValueAnimator.ofObject(ArgbEvaluator(), from, to)
    anim.duration = AnimationConstants.MEDIUM
    anim.interpolator = AnimationConstants.LINEAR_OUT_SLOW_IN
    anim.addUpdateListener { update(it.animatedValue as Int) }
    anim.start()
}

fun View.colorFade(@ColorRes to: Int) {
    if (background !is ColorDrawable) {
        throw IllegalArgumentException("View needs to have a ColorDrawable background in order to animate the color")
    }
    val from = (background as ColorDrawable).color
    colorFade(from, to) { setBackgroundColor(it) }
}
