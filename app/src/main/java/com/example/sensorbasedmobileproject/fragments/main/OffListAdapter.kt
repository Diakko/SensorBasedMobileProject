package com.example.sensorbasedmobileproject.fragments.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.OffItem
import kotlinx.android.synthetic.main.custom_row_off.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL


class OffListAdapter(private val context: Context) : RecyclerView.Adapter<OffListAdapter.MyViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var foodList = emptyList<OffItem>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_row_off, parent, false)


        return MyViewHolder(view).listen { pos, _ ->
            val ean = foodList[pos].code.toString()
            Navigation.findNavController(view).navigate(
                MainFragmentDirections.actionActionHomeToDetailsFragment(ean)
            )
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = foodList[position]
        holder.itemView.product_name.text = currentItem.product_name

        if (currentItem.manufacturing_places.equals("") || currentItem.manufacturing_places == null) {
            holder.itemView.manufacturing_places.text = R.string.unknown_country.toString()
        } else {
            "Made in: ${currentItem.manufacturing_places}".also {
                holder.itemView.manufacturing_places.text = it
            }
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
            holder.itemView.allergens_from_ingredients.text = R.string.no_allergens.toString()
            holder.itemView.off_card.product_image.setBackgroundColor(noErrorColor)
            holder.itemView.off_card.setBackgroundColor(onNoErrorColor)
        } else {
            // If allergens found, set image background color to RED and background light pink
            holder.itemView.off_card.product_image.setBackgroundColor(errorColor)
            holder.itemView.off_card.setBackgroundColor(onErrorColor)

            // Display allergens
            "Allergens: ${currentItem.allergens_from_ingredients}".also {
                holder.itemView.allergens_from_ingredients.text = it
            }
        }
        "EAN: ${currentItem.code.toString()}".also { holder.itemView.code.text = it }

        // If image_url is provided, get the image and display it
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
