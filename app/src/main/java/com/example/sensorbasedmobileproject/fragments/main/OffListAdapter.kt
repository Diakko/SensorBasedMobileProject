/**
 * Description:
 *
 * ListAdapter for MainFragments recyclerview
 * - Handles navigation to product details
 * - Populates viewholders with product detail data
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

package com.example.sensorbasedmobileproject.fragments.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.OffItem
import kotlinx.android.synthetic.main.custom_row_off.view.*
import kotlinx.android.synthetic.main.custom_row_off.view.off_card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class OffListAdapter(private val context: Context) : RecyclerView.Adapter<OffListAdapter.MyViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var foodList = emptyList<OffItem>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_row_off, parent, false)

        // Returns a viewholder with navigation to product details
        return MyViewHolder(view).listen { pos, _ ->
            val ean = foodList[pos].code.toString()
            Navigation.findNavController(view).navigate(
                MainFragmentDirections.actionActionHomeToDetailsFragment(ean)
            )
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Set the date for the search
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val currentDate = sdf.format(Date()).toString()
        holder.itemView.date.text =
            holder.itemView.context.getString(R.string.date_of_search, currentDate)

        // Get the current item in the list of items
        val currentItem = foodList[position]
        holder.itemView.product_name.text = currentItem.product_name

        if (currentItem.manufacturing_places.equals("") || currentItem.manufacturing_places == null) {
            holder.itemView.manufacturing_places.text =
                holder.itemView.context.getString(R.string.coo)

        } else {
            holder.itemView.manufacturing_places.text =
                holder.itemView.context.getString(R.string.made_in,
                    currentItem.manufacturing_places)
        }

        // Getting colors according to theme
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.colorError, typedValue, true)
        val errorColor = typedValue.data
        theme.resolveAttribute(R.attr.colorOnBackground, typedValue, true)
        val noErrorColor = typedValue.data
        theme.resolveAttribute(R.attr.colorOnError, typedValue, true)
        val onErrorColor = typedValue.data
        theme.resolveAttribute(R.attr.colorOnSurface, typedValue, true)
        val onNoErrorColor = typedValue.data

        // Check if allergens in product
        if (currentItem.allergens_from_ingredients.equals("")) {
            // No allergens found
            holder.itemView.allergens_from_ingredients.text = holder.itemView.context.getString(R.string.no_allergens_found)
            holder.itemView.off_card.product_image.setBackgroundColor(noErrorColor)
            holder.itemView.off_card.setBackgroundColor(onNoErrorColor)

        } else {

            // If allergens found, set image background color to RED and background light pink
            holder.itemView.off_card.product_image.setBackgroundColor(errorColor)
            holder.itemView.off_card.setBackgroundColor(onErrorColor)

            // Display allergens
            holder.itemView.allergens_from_ingredients.text =
                holder.itemView.context.getString(R.string.allergens_found,
                    currentItem.allergens_from_ingredients)
        }

        "EAN: ${currentItem.code.toString()}".also { holder.itemView.code.text = it }

        // If image_url is provided, get the image and display it
        if (currentItem.image_url != null) {
            val imageView = holder.itemView.findViewById<ImageView>(R.id.product_image)
            val url = URL(currentItem.image_url.toString())

            // Get image in a coroutine
            scope.launch(Dispatchers.IO) {
                launch {
                    val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    // Set image in Main thread
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
