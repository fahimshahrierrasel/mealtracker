package com.fahimshahrierrasel.mealtracker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.fahimshahrierrasel.mealtracker.controllers.CustomerController
import kotlinx.android.synthetic.main.activity_customer_register.*

class CustomerRegisterActivity() : AppCompatActivity() {

    private var customerNameEditText: EditText? = null
    private var floorNoEditText: EditText? = null
    private var bedNoEditText: EditText? = null
    private var mobileNoEditText: EditText? = null
    private var permitNoEditText: EditText? = null
    private var registerButton: Button? = null

    private val customerController = CustomerController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_register)
        setSupportActionBar(toolbar)

        registerButton = findViewById(R.id.customer_registration_button)
        customerNameEditText = findViewById(R.id.meal_date)
        floorNoEditText = findViewById(R.id.floor_no)
        bedNoEditText = findViewById(R.id.bed_no)
        mobileNoEditText = findViewById(R.id.mobile_no)
        permitNoEditText = findViewById(R.id.permit_no)

        registerButton?.setOnClickListener {
            val customerName = customerNameEditText?.text.toString()
            val floorNo = floorNoEditText?.text.toString()
            val bedNo = bedNoEditText?.text.toString()
            val mobileNo = mobileNoEditText?.text.toString()
            val permitNo = permitNoEditText?.text.toString()

            customerController.registerCustomer(customerName, floorNo, bedNo, mobileNo, permitNo)


            startActivity(Intent(this, AllCustomerActivity::class.java))
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
