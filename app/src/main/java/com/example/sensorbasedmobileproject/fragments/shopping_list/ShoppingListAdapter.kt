/**
 * Description:
 *
 * ListAdapter for shopping lists recycler view
 * - Handles clicks on item to strike through and
 * - Populates view holders with shopping list item.
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Matias Hätönen
 * Student number: 1902011
 */


package com.example.sensorbasedmobileproject.fragments.shopping_list

import android.content.Context
import android.graphics.Paint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.ShoppingListItem
import kotlinx.android.synthetic.main.shopping_list_item_row.view.*

class ShoppingListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class ShoppingListAdapter(private val contextHere: Context) : RecyclerView.Adapter<ShoppingListItemViewHolder>() {

    private var shoppingList = emptyList<ShoppingListItem>()

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListItemViewHolder {
        return ShoppingListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.shopping_list_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: ShoppingListItemViewHolder, positionShoppingList: Int) {

        // Colors
        val typedValue = TypedValue()
        val theme = contextHere.theme
        theme.resolveAttribute(R.attr.colorSecondary, typedValue, true)
        val colorOne = typedValue.data
        theme.resolveAttribute(R.attr.colorSecondaryVariant, typedValue, true)
        val colorTwo = typedValue.data
        val colors = mutableListOf(colorOne, colorTwo)
        val radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, contextHere.resources.displayMetrics)
        val currentItem = shoppingList[positionShoppingList]
        holder.itemView.text_view.text = currentItem.toString()
        holder.itemView.text_view.paintFlags = 0
        holder.itemView.shopping_list_card_view.setCardBackgroundColor(colors[positionShoppingList % 2])
        holder.itemView.shopping_list_card_view.radius = radius


        holder.itemView.setOnClickListener {
            if (holder.itemView.text_view.paintFlags > 0 && Paint.STRIKE_THRU_TEXT_FLAG > 0) {
                holder.itemView.text_view.paintFlags = 0
            } else {
                holder.itemView.text_view.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }

    fun setData(shoppingList: List<ShoppingListItem>) {
        this.shoppingList = shoppingList
        notifyDataSetChanged()
    }


}