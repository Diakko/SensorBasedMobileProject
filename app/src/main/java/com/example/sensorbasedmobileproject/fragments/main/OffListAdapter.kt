/**
 * Description:
 *
 * ListAdapter for MainFragments recyclerview
 * - Handles navigation to product details
 * - Populates viewholders with product detail data
 * - Handles the logic of coloring the recyclerview cards
 * according to user added allergens
 *
 * Course: Sensor Based Mobile Applications TX00CK66-3009
 * Name: Ville Pystynen
 * Student number: 1607999
 */

package com.example.sensorbasedmobileproject.fragments.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.LocaleListCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorbasedmobileproject.R
import com.example.sensorbasedmobileproject.data.OffItem
import com.example.sensorbasedmobileproject.utils.Constants
import kotlinx.android.synthetic.main.custom_row_off.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class OffListAdapter(private val context: Context) :
    RecyclerView.Adapter<OffListAdapter.MyViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var foodList = emptyList<OffItem>()
    private var allergenList = mutableListOf<String>()

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

        if (allergenList.size == 0) {
            // Get shared preferences reference
            val sharedPref =
                context.getSharedPreferences(Constants.ALLERGY_PREFERENCES, Context.MODE_PRIVATE)
            val map: Map<String, *> = sharedPref.all

            for ((k) in map) {
                allergenList.add(k)
            }
            Log.d("DBGITallergenList", allergenList.toString())
        } else {
            Log.d("DBGITallergenListNon0", allergenList.toString())
        }

        // Set the date for the search
        val ge: Locale = Locale.GERMAN
        val sdf = SimpleDateFormat("dd.MM.yyyy", ge)
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

        val radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            6f,
            context.resources.displayMetrics)

        // All cards are green
        holder.itemView.allergens_from_ingredients.text =
            holder.itemView.context.getString(R.string.no_allergens_found)
        holder.itemView.off_card.product_image.setBackgroundColor(noErrorColor)
        holder.itemView.off_card.setCardBackgroundColor(onNoErrorColor)
        holder.itemView.off_card.radius = radius

        // Except user added allergens
        if (allergenList.size > 0) {
            val conf: Configuration = context.resources.configuration
            val localeOriginal = Locale.getDefault()
            // Iterate trough the user added allergens
            allergenList.forEach {
                Log.d("DBGIT", it)
                if (it != "0" || it.isNotEmpty()) {
                    // If user added allergen is a substring of the allergens from ingredients
                    Constants.LIST_OF_LOCALISATIONS.forEach { localisation ->

                        val locale: Locale = Locale.forLanguageTag(localisation)
                        val it2 = getLocalizedResources(locale, it).toLowerCase()
                        Log.d("DBGIT2", it2)
                        if (it2 in (currentItem.allergens_from_ingredients?.toLowerCase())!!) {

                            // If allergens found, set image background color to RED and background light pink
                            holder.itemView.off_card.product_image.setBackgroundColor(errorColor)
                            holder.itemView.off_card.setCardBackgroundColor(onErrorColor)
                            holder.itemView.off_card.radius = radius
                        }
                    }
                }
            }
            conf.setLocale(localeOriginal)
        }
        // Display allergens
        holder.itemView.allergens_from_ingredients.text =
            holder.itemView.context.getString(R.string.allergens_found,
                currentItem.allergens_from_ingredients)

        "EAN: ${currentItem.code.toString()}".also { holder.itemView.code.text = it }

        // If image_url is provided, get the image and display it
        if (currentItem.image_url != null) {
            val imageView = holder.itemView.findViewById<ImageView>(R.id.product_image)
            val url = URL(currentItem.image_url.toString())

            // Get image in a coroutine
            scope.launch(Dispatchers.IO) {
                launch {
                    val bmp =
                        BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    // Set image in Main thread
                    Handler(Looper.getMainLooper()).post {
                        imageView.setImageBitmap(bmp)
                    }
                }
            }
        }
    }

    private fun getLocalizedResources(desiredLocale: Locale, allergen: String) : String {
        val conf: Configuration = context.resources.configuration
        conf.setLocale(desiredLocale)
        val localizedContext = context.createConfigurationContext(conf)
        val id = context.resources.getIdentifier(allergen, "string", context.packageName )
        val string = localizedContext.resources.getString(id)
        conf.setLocale(Locale.forLanguageTag("en"))
        return string
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
