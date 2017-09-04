package com.fahimshahrierrasel.mealtracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.fahimshahrierrasel.mealtracker.adapters.FeaturesAdapter
import com.fahimshahrierrasel.mealtracker.models.Feature
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var allFeatures: ArrayList<Feature> = ArrayList()

    private var featureRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        allFeatures = ArrayList()

        allFeatures.add(Feature("Customer Registration", R.drawable.user, CustomerRegisterActivity::class.java))
        allFeatures.add(Feature("All Customer", R.drawable.group, AllCustomerActivity::class.java))
        allFeatures.add(Feature("All Payments", R.drawable.cash, AllPaymentActivity::class.java))

        featureRecyclerView = findViewById(R.id.featureRecyclerView)

        current_date.text = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.US).format(Calendar.getInstance().time).toString()

        val featureAdapter = FeaturesAdapter(this, allFeatures)
        featureRecyclerView?.adapter = featureAdapter
        featureRecyclerView?.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

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
