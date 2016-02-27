package cc.postsoft.chompy.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.EXACTLY
import android.widget.ImageView
import cc.postsoft.chompy.R

class AspectRatioImageView : ImageView {
    private var widthRatio: Int = 1
    private var heightRatio: Int = 1

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
        widthRatio = a.getInteger(R.styleable.AspectRatioImageView_widthRatio, 1)
        heightRatio = a.getInteger(R.styleable.AspectRatioImageView_heightRatio, 1)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == EXACTLY) {
            if (heightMode != EXACTLY) {
                heightSize = (widthSize * 1f / widthRatio * heightRatio).toInt()
            }
        } else if (heightMode == EXACTLY) {
            widthSize = (heightSize * 1f / heightRatio * widthRatio).toInt()
        } else {
            throw IllegalStateException("Either width or height must be EXACTLY.")
        }

        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, EXACTLY)
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize, EXACTLY)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun setRatio(widthRatio: Int, heightRatio: Int) {
        this.widthRatio = widthRatio
        this.heightRatio = heightRatio
    }
}
