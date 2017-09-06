package com.fahimshahrierrasel.mealtracker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.fahimshahrierrasel.mealtracker.controllers.CustomerController
import com.fahimshahrierrasel.mealtracker.fragments.CustomerPaymentFragment
import com.fahimshahrierrasel.mealtracker.fragments.CustomerProfileFragment
import com.fahimshahrierrasel.mealtracker.utils.CaptureImage
import kotlinx.android.synthetic.main.activity_customer_details.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CustomerDetailsActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 111

    private var customerController = CustomerController()
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    var customerUUID: String? = null
    private var captureImage: CaptureImage? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setImageResource(R.drawable.ic_camera_alt_white_24dp)

        captureImage = CaptureImage(this)

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

        val customer = customerController.getCustomerBy(customerUUID!!)
        val customerName = customer.name
        val customerImage = customer.photo

        if (!customerImage.isEmpty()) {
            val image = captureImage!!.decodeFromBase64Image(customerImage)
            backdrop.setImageBitmap(image)
        }

        window.statusBarColor = Color.TRANSPARENT

        supportActionBar?.title = customerName

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter

        tabs.addTab(tabs.newTab().setText("Profile"))
        tabs.addTab(tabs.newTab().setText("Payment"))
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }
            override fun onPageSelected(position: Int) {
                when (position){
                    0-> fab.setImageResource(R.drawable.ic_camera_alt_white_24dp)
                    1-> fab.setImageResource(R.drawable.ic_playlist_add_white_24dp)
                }
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })

        fab.setOnClickListener { _ ->

            val current = container.currentItem
            println(current)

            when(current){
                0 -> {
                    captureImage!!.onLaunchCamera()
                }
                1 -> {
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
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == REQUEST_IMAGE_CAPTURE && data != null) {
            println("Image Request Accepted")
            if (requestCode == Activity.RESULT_OK) {
                val extras = data.extras
                val imageBitmap = extras.get("data") as Bitmap
                val imageBase64 = captureImage!!.encodeBitmapAndSaveToRealm(imageBitmap)
                customerController.addCustomerImage(customerUUID!!, imageBase64!!)
            }else if(requestCode == Activity.RESULT_CANCELED){
                println("Cancel")
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_customer_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_settings) {
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
