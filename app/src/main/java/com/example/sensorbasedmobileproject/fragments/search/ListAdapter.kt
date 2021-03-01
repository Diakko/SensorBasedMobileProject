package com.example.sensorbasedmobileproject.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.FineliItem
import kotlinx.android.synthetic.main.custom_row_fineli.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var foodList = emptyList<FineliItem>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_fineli, parent, false))
    }

    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {
        val currentItem = foodList[position]
        holder.itemView.fineli_id.text = currentItem.fineliId.toString()
        holder.itemView.fineli_name.text = currentItem.name?.fi
        holder.itemView.fineli_description.text = currentItem.ingredientClass?.description?.fi_d

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun setData(fineli: List<FineliItem>) {
        this.foodList = fineli
        notifyDataSetChanged()
    }



}