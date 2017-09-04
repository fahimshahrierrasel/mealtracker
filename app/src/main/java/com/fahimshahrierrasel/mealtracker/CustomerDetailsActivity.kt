package com.fahimshahrierrasel.mealtracker

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.fahimshahrierrasel.mealtracker.controllers.CustomerController
import com.fahimshahrierrasel.mealtracker.fragments.CustomerPaymentFragment
import com.fahimshahrierrasel.mealtracker.fragments.CustomerProfileFragment
import kotlinx.android.synthetic.main.activity_customer_details.*
import java.text.SimpleDateFormat
import java.util.*


class CustomerDetailsActivity : AppCompatActivity() {

    private var customerController = CustomerController()
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    var customerUUID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                customerUUID = null
            } else {
                customerUUID = extras.getString("CUSTOMER_UUID")
            }
        } else {
            customerUUID = savedInstanceState.getSerializable("CUSTOMER_UUID") as String
        }

        val customerName = customerController.getCustomerBy(customerUUID!!).name

        supportActionBar?.title = customerName

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter


        tabs.addTab(tabs.newTab().setText("Profile"))
        tabs.addTab(tabs.newTab().setText("Payment"))
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))


        fab.setOnClickListener { _ ->

            val dialogView = layoutInflater.inflate(R.layout.payment_input_layout, null)


            val dateText = dialogView.findViewById<EditText>(R.id.payment_input_date)

            dateText.text = SpannableStringBuilder(SimpleDateFormat("dd/MM/yyyy", Locale.US).format(Calendar.getInstance().time))


            val dialog = MaterialDialog.Builder(this)
                    .title("Add Payment")
                    .customView(dialogView, true)
                    .positiveText("Add")
                    .onPositive { dialog, _ ->

                        val paymentDateEditText = dialog.customView!!.findViewById<EditText>(R.id.payment_input_date)
                        val paymentAmountEditText = dialog.customView!!.findViewById<EditText>(R.id.paymeny_input_price)

                        val paymentDate = paymentDateEditText.text.toString()
                        val paymentAmount = paymentAmountEditText.text.toString()

                        if(paymentDate.isEmpty() || paymentAmount.isEmpty()){
                            Toast.makeText(this, "Please fill all information", Toast.LENGTH_SHORT).show()
                        }else{
                            customerController.addCustomerPayment(customerUUID!!, paymentDate, paymentAmount)

                            Toast.makeText(this, "Payment Successfully Add!", Toast.LENGTH_SHORT).show()
                        }


                    }
            dialog.show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_customer_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position){
                0 -> CustomerProfileFragment.newInstance(customerUUID!!)
                1 -> CustomerPaymentFragment.newInstance(customerUUID!!)
                else -> CustomerProfileFragment.newInstance(customerUUID!!)
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }


}
