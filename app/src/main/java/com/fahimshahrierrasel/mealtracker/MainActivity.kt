package com.fahimshahrierrasel.mealtracker

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.fahimshahrierrasel.mealtracker.models.Feature

import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.StaggeredGridLayoutManager
import com.fahimshahrierrasel.mealtracker.adapters.FeaturesAdapter
import android.support.v7.widget.RecyclerView



class MainActivity : AppCompatActivity() {

    var allFeatures: ArrayList<Feature> = ArrayList()

    private var featureRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        allFeatures = ArrayList()

        allFeatures.add(Feature("Customer Registration", R.drawable.boss, CustomerRegisterActivity::class.java))
        allFeatures.add(Feature("All Customer", R.drawable.boss, AllCustomerActivity::class.java))


        featureRecyclerView = findViewById(R.id.featureRecyclerView)

        val featureAdapter = FeaturesAdapter(this, allFeatures)
        featureRecyclerView?.adapter = featureAdapter
        featureRecyclerView?.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
