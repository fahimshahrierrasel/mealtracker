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
import io.realm.RealmList
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */

class PaymentsAdapter(val context: Context, var mPayments: RealmList<Payment>) : RecyclerView.Adapter<PaymentsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val featureView = inflater.inflate(R.layout.payment_layout, parent, false)
        return ViewHolder(featureView)
    }

    override fun onBindViewHolder(holder: PaymentsAdapter.ViewHolder, position: Int) {
        val aPayment = mPayments[position]

        holder.paymentDateTextView.text = aPayment.date
        holder.paymentAmountTextView.text = NumberFormat.getCurrencyInstance(Locale.US).format(aPayment.amount)
    }

    override fun getItemCount(): Int {
        return mPayments.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var paymentDateTextView: TextView = itemView.findViewById(R.id.payment_date)
        var paymentAmountTextView: TextView = itemView.findViewById(R.id.payment_amount)
        var payment_card: CardView = itemView.findViewById(R.id.payment_card)

        init {
        }
    }
}