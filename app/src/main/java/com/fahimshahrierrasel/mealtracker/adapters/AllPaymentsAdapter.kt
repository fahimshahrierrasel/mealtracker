package com.fahimshahrierrasel.mealtracker.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fahimshahrierrasel.mealtracker.R
import com.fahimshahrierrasel.mealtracker.models.Payment
import com.fahimshahrierrasel.mealtracker.models.UserPayment
import io.realm.RealmList
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */

class AllPaymentsAdapter(val context: Context, var mPayments: ArrayList<UserPayment>) : RecyclerView.Adapter<AllPaymentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllPaymentsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val featureView = inflater.inflate(R.layout.all_payment_layout, parent, false)
        return ViewHolder(featureView)
    }

    override fun onBindViewHolder(holder: AllPaymentsAdapter.ViewHolder, position: Int) {
        val aPayment = mPayments[position]

        holder.paymentUserNameTextView.text = aPayment.name
        holder.paymentDateTextView.text = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(aPayment.date).toString()
        holder.paymentAmountTextView.text = NumberFormat.getCurrencyInstance(Locale.US).format(aPayment.amount)
    }

    override fun getItemCount(): Int {
        return mPayments.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var paymentUserNameTextView: TextView = itemView.findViewById(R.id.payment_username)
        var paymentDateTextView: TextView = itemView.findViewById(R.id.payment_date)
        var paymentAmountTextView: TextView = itemView.findViewById(R.id.payment_amount)
        var payment_card: CardView = itemView.findViewById(R.id.payment_card)

        init {
        }
    }
}