package cc.femto.chompy.ui.main

import android.content.Context
import android.text.Layout
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import cc.femto.chompy.R
import cc.femto.chompy.data.api.model.MenuItem
import cc.femto.kommon.extensions.api23
import cc.femto.kommon.extensions.fade
import cc.femto.kommon.extensions.translate
import cc.femto.kommon.ui.Scrims
import com.jakewharton.rxbinding.view.globalLayouts
import com.squareup.picasso.Picasso
import org.jetbrains.anko.dip

class MenuItemRow(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    val photo: ImageView by bindView(R.id.photo)
    val scrim: ImageView by bindView(R.id.scrim)
    val detailScrim: View by bindView(R.id.detail_scrim)
    val dish: TextView by bindView(R.id.dish)
    val details: View by bindView(R.id.details)
    val restaurant: TextView by bindView(R.id.restaurant)
    val description: TextView by bindView(R.id.description)

    lateinit var clickListener: (MenuItem) -> Unit

    override fun onFinishInflate() {
        super.onFinishInflate()

        scrim.background = Scrims.PHOTO_SCRIM

        api23 {
            description.hyphenationFrequency = Layout.HYPHENATION_FREQUENCY_FULL
            description.breakStrategy = Layout.BREAK_STRATEGY_HIGH_QUALITY
        }

        setOnClickListener {
            if (details.visibility != VISIBLE) {
                val translation = -details.height
                dish.translate(translationY = translation + dip(16))
                details.translate()
                detailScrim.fade()
            } else {
                dish.translate()
                details.translate(translationY = details.height) { withEndAction { details.visibility = INVISIBLE } }
                detailScrim.fade(alpha = 0f)
            }
        }
    }

    /**
     * Cropping view to square
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }

    fun bindTo(menuItem: MenuItem, clickListener: (MenuItem) -> Unit, picasso: Picasso) {
        if (menuItem.imageUrl != null) {
            picasso.load(menuItem.imageUrl)
                    .placeholder(R.color.dark_gray)
                    .fit()
                    .centerCrop()
                    .into(photo)
        }
        dish.text = menuItem.dish
        restaurant.visibility = if (menuItem.restaurant?.isBlank() ?: true) GONE else VISIBLE
        val priceText = if (menuItem.price?.startsWith('$') ?: true) menuItem.price else "$" + menuItem.price
        restaurant.text = "${menuItem.restaurant}  •  $priceText"
//        description.visibility = if (menuItem.description?.isBlank() ?: true) GONE else VISIBLE
        description.text = menuItem.description?.trim()
        this.clickListener = clickListener
        // reset animation state
        detailScrim.alpha = 0f
        detailScrim.visibility = INVISIBLE
        dish.translationY = 0f
        details.visibility = INVISIBLE
        details.globalLayouts().limit(1).subscribe { details.translationY = details.height.toFloat() }
    }
}
