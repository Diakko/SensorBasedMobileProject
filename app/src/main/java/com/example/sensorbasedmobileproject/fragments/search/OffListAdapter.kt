package com.example.sensorbasedmobileproject.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.OffItem
import kotlinx.android.synthetic.main.custom_row_off.view.*

class OffListAdapter : RecyclerView.Adapter<OffListAdapter.MyViewHolder>() {

    private var foodList = emptyList<OffItem>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffListAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_off, parent, false))
    }

    override fun onBindViewHolder(holder: OffListAdapter.MyViewHolder, position: Int) {
        val currentItem = foodList[position]
        holder.itemView.ean.text = currentItem.id.toString()
//        holder.itemView.name.text = currentItem.name?.fi
//        holder.itemView.off_description.text = currentItem.ingredientClass?.description?.fi_d

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun setData(off: List<OffItem>) {
        this.foodList = off
        notifyDataSetChanged()
    }



}