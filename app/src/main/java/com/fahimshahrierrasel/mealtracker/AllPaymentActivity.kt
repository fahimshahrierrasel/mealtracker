package com.fahimshahrierrasel.mealtracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.fahimshahrierrasel.mealtracker.adapters.AllPaymentsAdapter
import com.fahimshahrierrasel.mealtracker.controllers.CustomerController
import com.fahimshahrierrasel.mealtracker.models.AllPayment
import kotlinx.android.synthetic.main.activity_all_payment.*
import kotlinx.android.synthetic.main.content_all_payment.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class AllPaymentActivity : AppCompatActivity(){

    private var calendar: Calendar? = null
    private var year: Int? = null
    private var month: Int? = null
    private var day: Int? = null
    private var allPayment: AllPayment? = null
    private var allPaymentAdapter: AllPaymentsAdapter? = null
    private val customerController = CustomerController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_payment)
        setSupportActionBar(toolbar)

        val todayDate = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(Calendar.getInstance().time)
        allPayment = customerController.getAllPaymentBy(todayDate)
        allPaymentAdapter = AllPaymentsAdapter(this, allPayment!!.allUserPayment)

        allPaymentRecyclerView.adapter = allPaymentAdapter
        allPaymentRecyclerView.layoutManager = LinearLayoutManager(this)

        total_amount.text = NumberFormat.getCurrencyInstance(Locale.US).format(allPayment!!.totalAmount)

        calendar = Calendar.getInstance()
        year = calendar!!.get(Calendar.YEAR)
        month = calendar!!.get(Calendar.MONTH)
        day = calendar!!.get(Calendar.DAY_OF_MONTH)

        date_selector.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { _ , year, month, day ->

                        val todayString = String.format("%02d/%02d/%04d", day,month+1,year)

                        allPayment = customerController.getAllPaymentBy(todayString)
                        allPaymentAdapter = AllPaymentsAdapter(this, allPayment!!.allUserPayment)
                        total_amount.text = NumberFormat.getCurrencyInstance(Locale.US).format(allPayment!!.totalAmount)
                        allPaymentRecyclerView.adapter = allPaymentAdapter

                    }, year!!, month!!, day!!)
            datePickerDialog.show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
