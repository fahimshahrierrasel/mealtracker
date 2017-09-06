package com.fahimshahrierrasel.mealtracker.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fahimshahrierrasel.mealtracker.CustomerDetailsActivity
import com.fahimshahrierrasel.mealtracker.R
import com.fahimshahrierrasel.mealtracker.models.Customer
import io.realm.RealmResults
import java.io.IOException

/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */

class CustomersAdapter(val context: Context, var mCustomers: RealmResults<Customer>) : RecyclerView.Adapter<CustomersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomersAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val featureView = inflater.inflate(R.layout.customer_layout, parent, false)
        return ViewHolder(featureView)
    }

    override fun onBindViewHolder(holder: CustomersAdapter.ViewHolder, position: Int) {
        val aCustomer = mCustomers[position]

        holder.customerNameTextView.text = context.getString(R.string.name_placeholder, aCustomer.name)
        holder.floorNoTextView.text = context.getString(R.string.floor_no_placeholder, aCustomer.floorNo)
        holder.bedNoTextView.text = context.getString(R.string.bed_no_placeholder, aCustomer.bedNo)
        holder.mobileNoTextView.text = context.getString(R.string.mobile_no_placeholder, aCustomer.mobileNo)
        holder.permitNoTextView.text = context.getString(R.string.permit_no_placeholder, aCustomer.permitNo)
        if (!aCustomer.photo.isEmpty()){
            holder.customerImage.setImageBitmap(decodeFromBase64Image(aCustomer.photo))
        }

        //holder.customerImage.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), aCustomer., null))
    }

    override fun getItemCount(): Int {
        return mCustomers.size
    }

    @Throws(IOException::class)
    fun decodeFromBase64Image(image: String): Bitmap {

        val decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var customerImage: ImageView
        var customerNameTextView: TextView
        var floorNoTextView: TextView
        var bedNoTextView: TextView
        var mobileNoTextView: TextView
        var permitNoTextView: TextView
        var customerCard: CardView

        init {

            customerCard = itemView.findViewById(R.id.customer_card)
            customerImage = itemView.findViewById(R.id.customer_image)
            customerNameTextView = itemView.findViewById(R.id.customer_name)
            floorNoTextView = itemView.findViewById(R.id.customer_floor_no)
            bedNoTextView = itemView.findViewById(R.id.customer_bed_no)
            mobileNoTextView = itemView.findViewById(R.id.customer_mobile_no)
            permitNoTextView = itemView.findViewById(R.id.customer_permit_no)

            customerCard.setOnClickListener {
                val position = adapterPosition
                println(position)
                if (position != RecyclerView.NO_POSITION) {
                    val customer = mCustomers[position]
                    val intent = Intent(context, CustomerDetailsActivity::class.java)
                    intent.putExtra("CUSTOMER_UUID", customer.id)
                    context.startActivity(intent)
                }
            }
        }


    }

}