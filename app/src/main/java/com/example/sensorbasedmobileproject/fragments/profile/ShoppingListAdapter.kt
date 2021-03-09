package com.example.sensorbasedmobileproject.fragments.profile

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.ShoppingListItem
import kotlinx.android.synthetic.main.shopping_list_item_row.view.*

class ShoppingListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class ShoppingListAdapter(context: Context) : RecyclerView.Adapter<ShoppingListItemViewHolder>() {

    private val colors = mutableListOf("#FFE3FE", "#B4AEE8")

    private var shoppingList = emptyList<ShoppingListItem>()

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListItemViewHolder {
        return ShoppingListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.shopping_list_item_row, parent, false))
    }

    override fun onBindViewHolder(holder: ShoppingListItemViewHolder, positionShoppingList: Int) {
        val currentItem = shoppingList[positionShoppingList]
        holder.itemView.text_view.text = currentItem.toString()
        holder.itemView.text_view.paintFlags = 0
        holder.itemView.setBackgroundColor(Color.parseColor(colors[positionShoppingList % 2]))

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