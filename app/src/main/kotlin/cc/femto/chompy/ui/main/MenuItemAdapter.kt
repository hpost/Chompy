package cc.femto.chompy.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import cc.femto.chompy.R
import cc.femto.chompy.data.api.model.MenuItem
import com.squareup.picasso.Picasso


class MenuItemAdapter(val clickListener: (MenuItem) -> Unit, val picasso: Picasso) :
        RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>() {

    private var menuItems = emptyList<MenuItem>()

    fun set(menuItems: List<MenuItem>?) {
        this.menuItems = menuItems ?: emptyList()
        notifyDataSetChanged()
    }

    override fun getItemCount() = menuItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_menu_item, parent, false) as MenuItemRow
        return MenuItemViewHolder(view, clickListener, picasso)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bindTo(menuItems[position])
    }

    class MenuItemViewHolder(private val view: MenuItemRow, val clickListener: (MenuItem) -> Unit, val picasso: Picasso) : RecyclerView.ViewHolder(view) {

        private lateinit var menuItem: MenuItem

        fun bindTo(menuItem: MenuItem) {
            this.menuItem = menuItem
            view.bindTo(menuItem, clickListener, picasso)
        }
    }
}



