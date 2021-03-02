package com.example.sensorbasedmobileproject.fragments.search

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.OffItem
import kotlinx.android.synthetic.main.custom_row_off.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL


class OffListAdapter : RecyclerView.Adapter<OffListAdapter.MyViewHolder>() {

    val scope = CoroutineScope(Dispatchers.IO)

    private var foodList = emptyList<OffItem>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffListAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_row_off,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OffListAdapter.MyViewHolder, position: Int) {
        val currentItem = foodList[position]
        holder.itemView.code.text = currentItem.code.toString()
        holder.itemView.product_name.text = currentItem.product_name
        holder.itemView.ingredients_text_debug.text = currentItem.ingredients_text_debug

        val imageView = holder.itemView.findViewById<ImageView>(R.id.product_image)
        Log.d("DBG", "IMAGE_URL" + currentItem.image_url)
        val url = URL(currentItem.image_url.toString())


        scope.launch(Dispatchers.IO) {
            launch {
                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                Handler(Looper.getMainLooper()).post {
                    imageView.setImageBitmap(bmp)
                }

            }
        }


    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun setData(off: List<OffItem>) {
        this.foodList = off
        notifyDataSetChanged()
    }


}