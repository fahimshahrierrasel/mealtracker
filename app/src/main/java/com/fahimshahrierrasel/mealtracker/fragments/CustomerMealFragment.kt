package com.fahimshahrierrasel.mealtracker.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahimshahrierrasel.mealtracker.R
import com.fahimshahrierrasel.mealtracker.adapters.MealsAdapter
import com.fahimshahrierrasel.mealtracker.controllers.CustomerController
import com.fahimshahrierrasel.mealtracker.models.Customer
import kotlinx.android.synthetic.main.fragment_customer_meal.*

/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */

class CustomerMealFragment() : Fragment() {


    var customerController = CustomerController()
    var mealRecyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.meal_layout, container, false)

        val customerUUID = arguments.getString(CUSTOMER_UUID)

        val customer: Customer = customerController.getCustomerBy(customerUUID)

        val mealsAdapter = MealsAdapter(activity, customer.meals!!)

        mealRecyclerView = rootView.findViewById(R.id.mealRecyclerView)

        mealRecyclerView?.adapter = mealsAdapter
        mealRecyclerView?.layoutManager = LinearLayoutManager(activity)

        return rootView
    }


    companion object {
        private val CUSTOMER_UUID = "UUID"

        fun newInstance(customerUUID: String): CustomerMealFragment {
            val fragment = CustomerMealFragment()
            val args = Bundle()
            args.putString(CUSTOMER_UUID, customerUUID)
            fragment.arguments = args
            return fragment
        }
    }
}