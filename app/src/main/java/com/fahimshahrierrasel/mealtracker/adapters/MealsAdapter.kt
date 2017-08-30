package com.fahimshahrierrasel.mealtracker.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fahimshahrierrasel.mealtracker.R
import com.fahimshahrierrasel.mealtracker.models.Meal
import io.realm.RealmList

/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */

class MealsAdapter(val context: Context, var mMeals: RealmList<Meal>) : RecyclerView.Adapter<MealsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val featureView = inflater.inflate(R.layout.meal_layout, parent, false)
        return ViewHolder(featureView)
    }

    override fun onBindViewHolder(holder: MealsAdapter.ViewHolder, position: Int) {
        val aMeal = mMeals[position]

        holder.mealDate.text = aMeal.date.toString()
        holder.mealType.text = aMeal.type
        holder.mealPrice.text = aMeal.price.toString()

    }

    override fun getItemCount(): Int {
        return mMeals.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mealDate: TextView = itemView.findViewById(R.id.meal_date)
        var mealType: TextView = itemView.findViewById(R.id.meal_type)
        var mealPrice: TextView = itemView.findViewById(R.id.meal_price)
        var mealCard: CardView = itemView.findViewById(R.id.meal_card)

        init {
            mealCard.setOnClickListener {
                val position = adapterPosition
                println(position)
                if (position != RecyclerView.NO_POSITION) {
                    //val customer = mCustomers[position]
                    //val intent = Intent(context, CustomerDetailsActivity::class.java)
                    //intent.putExtra("CUSTOMER_UUID", customer.id)
                    //context.startActivity(intent)
                    //Toast.makeText(context, customer.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}