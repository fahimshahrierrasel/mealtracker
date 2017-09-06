package com.fahimshahrierrasel.mealtracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.fahimshahrierrasel.mealtracker.adapters.FeaturesAdapter
import com.fahimshahrierrasel.mealtracker.controllers.CustomerController
import com.fahimshahrierrasel.mealtracker.models.Feature
import com.fahimshahrierrasel.mealtracker.utils.RealmBackupRestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var allFeatures: ArrayList<Feature> = ArrayList()
    private val firebaseStorage = FirebaseStorage.getInstance()

    private var featureRecyclerView: RecyclerView? = null
    private var realmBR: RealmBackupRestore? = null

    val cusController = CustomerController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        realmBR = RealmBackupRestore(this.applicationContext, firebaseStorage)

        allFeatures = ArrayList()

        allFeatures.add(Feature("Customer Registration", R.drawable.user, CustomerRegisterActivity::class.java))
        allFeatures.add(Feature("All Customer", R.drawable.group, AllCustomerActivity::class.java))
        allFeatures.add(Feature("All Payments", R.drawable.cash, AllPaymentActivity::class.java))

        featureRecyclerView = findViewById<RecyclerView>(R.id.featureRecyclerView)

        current_date.text = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.US).format(Calendar.getInstance().time).toString()

        val featureAdapter = FeaturesAdapter(this, allFeatures)
        featureRecyclerView?.adapter = featureAdapter
        featureRecyclerView?.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)


        if (cusController.getAllCustomer()!!.size < 1) {
            realmBR!!.downloadFromFirebase()
            realmBR!!.restore()
        } else if (cusController.getAllCustomer()!!.size >= 1) {
            realmBR!!.backup()
            realmBR!!.uploadToFirebase()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
