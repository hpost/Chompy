package cc.femto.kommon.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import cc.femto.kommon.Kommon

fun Context.dip(value: Int): Int = (value * (resources.displayMetrics.density)).toInt()
fun Context.dimen(@DimenRes resourceId: Int): Float = resources.getDimension(resourceId)
fun Context.integer(@IntegerRes resourceId: Int): Int = resources.getInteger(resourceId)
fun Context.bool(@BoolRes resourceId: Int): Boolean = resources.getBoolean(resourceId)
fun Context.color(@ColorRes resourceId: Int): Int = ContextCompat.getColor(this, resourceId)
fun Context.colorStateList(@DrawableRes resourceId: Int): ColorStateList = ContextCompat.getColorStateList(this, resourceId)
fun Context.drawable(@DrawableRes resourceId: Int): Drawable = ContextCompat.getDrawable(this, resourceId)
fun Context.drawable(@DrawableRes resourceId: Int, tintColorResId: Int): Drawable {
    val drawable = ContextCompat.getDrawable(this, resourceId)
    drawable.setTint(color(tintColorResId))
    return drawable
}

fun Context.string(@StringRes resourceId: Int): String = resources.getString(resourceId)
fun Context.string(@StringRes resourceId: Int, vararg args: Any?): String = resources.getString(resourceId, *args)
fun Context.quantityString(@PluralsRes resourceId: Int, quantity: Int): String = resources.getQuantityString(resourceId, quantity, quantity)

fun View.dip(value: Int): Int = (value * (resources.displayMetrics.density)).toInt()
fun View.dimen(@DimenRes resourceId: Int): Float = resources.getDimension(resourceId)
fun View.integer(@IntegerRes resourceId: Int): Int = resources.getInteger(resourceId)
fun View.bool(@BoolRes resourceId: Int): Boolean = resources.getBoolean(resourceId)
fun View.color(@ColorRes resourceId: Int): Int = ContextCompat.getColor(ctx, resourceId)
fun View.colorStateList(@DrawableRes resourceId: Int): ColorStateList = ContextCompat.getColorStateList(ctx, resourceId)
fun View.drawable(@DrawableRes resourceId: Int): Drawable = ContextCompat.getDrawable(ctx, resourceId)
fun View.drawable(@DrawableRes resourceId: Int, tintColorResId: Int): Drawable {
    val drawable = ContextCompat.getDrawable(ctx, resourceId)
    drawable.setTint(color(tintColorResId))
    return drawable
}
fun View.string(@StringRes resourceId: Int): String = resources.getString(resourceId)
fun View.string(@StringRes resourceId: Int, vararg args: Any?): String = resources.getString(resourceId, *args)
fun View.quantityString(@PluralsRes resourceId: Int, quantity: Int, vararg args: Any?): String = resources.getQuantityString(resourceId, quantity, quantity, *args)

fun dip(value: Int): Int = (value * (Kommon.ctx.resources.displayMetrics.density)).toInt()
fun dimen(@DimenRes resourceId: Int): Float = Kommon.ctx.resources.getDimension(resourceId)
fun integer(@IntegerRes resourceId: Int): Int = Kommon.ctx.resources.getInteger(resourceId)
fun bool(@BoolRes resourceId: Int): Boolean = Kommon.ctx.resources.getBoolean(resourceId)
fun color(@ColorRes resourceId: Int): Int = ContextCompat.getColor(Kommon.ctx, resourceId)
fun colorStateList(@DrawableRes resourceId: Int): ColorStateList = ContextCompat.getColorStateList(Kommon.ctx, resourceId)
fun drawable(@DrawableRes resourceId: Int): Drawable = ContextCompat.getDrawable(Kommon.ctx, resourceId)
fun string(@StringRes resourceId: Int): String = Kommon.ctx.resources.getString(resourceId)
fun string(@StringRes resourceId: Int, vararg args: Any?): String = Kommon.ctx.resources.getString(resourceId, *args)
fun quantityString(@PluralsRes resourceId: Int, quantity: Int): String = Kommon.ctx.resources.getQuantityString(resourceId, quantity, quantity)

val toolbarHeight: Int = -1
    get() {
        if (field < 0) {
            val value = TypedValue()
            Kommon.ctx.theme.resolveAttribute(android.R.attr.actionBarSize, value, true)
            field = TypedValue.complexToDimensionPixelSize(value.data, Kommon.ctx.resources.displayMetrics)
        }
        return field
    }
