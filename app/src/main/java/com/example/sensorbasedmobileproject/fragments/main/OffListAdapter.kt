package com.example.sensorbasedmobileproject.fragments.main

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.OffItem
import kotlinx.android.synthetic.main.custom_row_off.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL


class OffListAdapter : RecyclerView.Adapter<OffListAdapter.MyViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var foodList = emptyList<OffItem>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_row_off, parent, false)

        return MyViewHolder(view).listen { pos, _ ->
            val item = foodList[pos]
            // Navigate to details_fragment
            // From that fragment do a Fineli search and show results
            Navigation.findNavController(view).navigate(
                MainFragmentDirections.actionActionHomeToDetailsFragment(item.toString())
            )
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = foodList[position]
        "EAN: ${currentItem.code.toString()}".also { holder.itemView.code.text = it }
        holder.itemView.product_name.text = currentItem.product_name
        holder.itemView.ingredients_text_debug.text = currentItem.ingredients_text_debug

        if (currentItem.image_url != null) {
            val imageView = holder.itemView.findViewById<ImageView>(R.id.product_image)
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
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun setData(off: List<OffItem>) {
        this.foodList = off
        notifyDataSetChanged()
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType)
        }
        return this
    }

}
