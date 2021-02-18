package com.example.sensorbasedmobileproject.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.FineliItem
import com.example.sensorbasedmobileproject.model.Fineli
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var foodList = emptyList<FineliItem>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {
        val currentItem = foodList[position]
        holder.itemView.fineli_id.text = currentItem.fineliId.toString()
        holder.itemView.fineli_kcal.text = currentItem.energyKcal.toString()
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun setData(fineli: List<FineliItem>) {
        this.foodList = fineli
        notifyDataSetChanged()
    }



}