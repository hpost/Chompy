package cc.postsoft.chompy.ui.main

import android.content.Context
import android.text.Layout
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.bindView
import cc.postsoft.chompy.R
import cc.postsoft.chompy.data.api.model.MenuItem
import cc.postsoft.chompy.extensions.api23
import cc.postsoft.chompy.ui.common.Scrims
import com.squareup.picasso.Picasso

class MenuItemRow(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    val photo: ImageView by bindView(R.id.photo)
    val scrim: ImageView by bindView(R.id.scrim)
    val preview: TextView by bindView(R.id.preview)
    val details: View by bindView(R.id.details)
    val dish: TextView by bindView(R.id.dish)
    val restaurant: TextView by bindView(R.id.restaurant)
    val description: TextView by bindView(R.id.description)
    val price: TextView by bindView(R.id.price)

    lateinit var clickListener: (MenuItem) -> Unit

    override fun onFinishInflate() {
        super.onFinishInflate()

        scrim.background = Scrims.PHOTO_SCRIM

        api23 {
            description.hyphenationFrequency = Layout.HYPHENATION_FREQUENCY_FULL
            description.breakStrategy = Layout.BREAK_STRATEGY_HIGH_QUALITY
        }
        setOnClickListener {
            if (details.visibility != View.VISIBLE) {
                preview.visibility = View.INVISIBLE
                details.visibility = View.VISIBLE
            } else {
                details.visibility = View.INVISIBLE
                preview.visibility = View.VISIBLE
            }
        }
    }

    fun bindTo(menuItem: MenuItem, clickListener: (MenuItem) -> Unit, picasso: Picasso) {
        if (menuItem.imageUrl != null) {
            picasso.load(menuItem.imageUrl)
                    .placeholder(R.color.dark_gray)
                    .fit()
                    .centerCrop()
                    .into(photo)
        }
        preview.text = menuItem.dish
        preview.visibility = View.VISIBLE
        dish.text = menuItem.dish
        restaurant.visibility = if (menuItem.restaurant?.isBlank() ?: true) View.GONE else View.VISIBLE
        restaurant.text = menuItem.restaurant
        description.visibility = if (menuItem.description?.isBlank() ?: true) View.GONE else View.VISIBLE
        description.text = menuItem.description
        price.text = if (menuItem.price?.startsWith('$') ?: true) menuItem.price else "$" + menuItem.price
        details.visibility = View.INVISIBLE
        this.clickListener = clickListener
    }
}
