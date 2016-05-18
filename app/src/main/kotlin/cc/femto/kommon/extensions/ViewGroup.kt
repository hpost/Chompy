package cc.femto.kommon.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

operator fun ViewGroup.get(position: Int): View = getChildAt(position)

fun ViewGroup.inflate(@LayoutRes layoutResId: Int, inflater: LayoutInflater? = null): View? =
        if (inflater != null) inflater.inflate(layoutResId, this, false)
        else LayoutInflater.from(ctx).inflate(layoutResId, this, false)

