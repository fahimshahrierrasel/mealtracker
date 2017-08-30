package com.fahimshahrierrasel.mealtracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import com.fahimshahrierrasel.mealtracker.adapters.CustomersAdapter
import com.fahimshahrierrasel.mealtracker.controllers.CustomerController
import com.fahimshahrierrasel.mealtracker.models.Customer
import io.realm.RealmResults


enum class SearchOption {
    NAME,
    FLOOR,
    BED,
    MOBILE,
    PERMIT
}

class AllCustomerActivity : AppCompatActivity() {
    private val customerController = CustomerController()

    private var customersAdapter: CustomersAdapter? = null
    private var allCustomers: RealmResults<Customer>? = null

    var searchOption = SearchOption.NAME

    private var customerRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_customer)

        allCustomers = customerController.getAllCustomer()!!
        customerRecyclerView = findViewById(R.id.customerRecyclerView)
        customersAdapter = CustomersAdapter(this, allCustomers!!)
        customerRecyclerView?.adapter = customersAdapter
        customerRecyclerView?.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_all_customer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                searchCustomer(item)
                return true
            }
            R.id.action_search_options ->{
                MaterialDialog.Builder(this)
                        .title("Search Option")
                        .items(R.array.search_option)
                        .itemsCallbackSingleChoice(0) { dialog, _, which, _ ->
                            when(which){
                                0 -> searchOption = SearchOption.NAME
                                1 -> searchOption = SearchOption.FLOOR
                                2 -> searchOption = SearchOption.BED
                                3 -> searchOption = SearchOption.MOBILE
                                4 -> searchOption = SearchOption.PERMIT
                            }
                            dialog.selectedIndex = which
                            true
                        }
                        .positiveText("Choose")
                        .show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun searchCustomer(item: MenuItem) {
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                when(searchOption){
                    SearchOption.NAME -> prepareRecyclerViewForSearch("name", query)
                    SearchOption.FLOOR -> prepareRecyclerViewForSearch("floorNo", query)
                    SearchOption.BED -> prepareRecyclerViewForSearch("bedNo", query)
                    SearchOption.MOBILE -> prepareRecyclerViewForSearch("mobileNo", query)
                    SearchOption.PERMIT -> prepareRecyclerViewForSearch("permitNo", query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                when(searchOption){
                    SearchOption.NAME -> prepareRecyclerViewForSearch("name", newText)
                    SearchOption.FLOOR -> prepareRecyclerViewForSearch("floorNo", newText)
                    SearchOption.BED -> prepareRecyclerViewForSearch("bedNo", newText)
                    SearchOption.MOBILE -> prepareRecyclerViewForSearch("mobileNo", newText)
                    SearchOption.PERMIT -> prepareRecyclerViewForSearch("permitNo", newText)
                }
                return true
            }
        })

        searchView.setOnCloseListener {
            allCustomers = customerController.getAllCustomer()
            customersAdapter = CustomersAdapter(this@AllCustomerActivity, allCustomers!!)
            customerRecyclerView?.adapter = customersAdapter
            true
        }
    }

    private fun prepareRecyclerViewForSearch(option: String, key: String){
        allCustomers = customerController.searchCustomerBy(option, key)
        customersAdapter = CustomersAdapter(this@AllCustomerActivity, allCustomers!!)
        customerRecyclerView?.adapter = customersAdapter
    }
}
