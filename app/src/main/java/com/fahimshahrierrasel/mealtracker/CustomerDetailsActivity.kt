package com.fahimshahrierrasel.mealtracker

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import com.fahimshahrierrasel.mealtracker.fragments.CustomerMealFragment
import com.fahimshahrierrasel.mealtracker.fragments.CustomerProfileFragment
import com.fahimshahrierrasel.mealtracker.fragments.PlaceholderFragment
import kotlinx.android.synthetic.main.activity_customer_details.*
import com.afollestad.materialdialogs.MaterialDialog
import android.text.InputType
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.widget.EditText
import kotlinx.android.synthetic.main.meal_input_layout.*
import java.text.SimpleDateFormat
import java.util.*




class CustomerDetailsActivity : AppCompatActivity() {

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


        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter


        tabs.addTab(tabs.newTab().setText("Profile"))
        tabs.addTab(tabs.newTab().setText("Meal"))
        tabs.addTab(tabs.newTab().setText("Payment"))
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))


        fab.setOnClickListener { view ->

            val dialogView = layoutInflater.inflate(R.layout.meal_input_layout, null)


            val dateText = dialogView.findViewById<EditText>(R.id.meal_input_date)

            dateText.text = SpannableStringBuilder(SimpleDateFormat("dd/mm/yyyy", Locale.US).format(Calendar.getInstance().time))


            val dialog = MaterialDialog.Builder(this)
                    .title("Meal Input")
                    .customView(dialogView, true)
                    .positiveText("Add")

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
            when (position){
                0 -> return CustomerProfileFragment.newInstance(customerUUID!!)
                1 -> return CustomerMealFragment.newInstance(customerUUID!!)
                else -> return PlaceholderFragment.newInstance(position + 1)
            }

        }

        override fun getCount(): Int {
            return 3
        }
    }


}
