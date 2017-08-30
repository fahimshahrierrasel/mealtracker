package com.fahimshahrierrasel.mealtracker.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahimshahrierrasel.mealtracker.R
import com.fahimshahrierrasel.mealtracker.controllers.CustomerController
import com.fahimshahrierrasel.mealtracker.models.Customer
import kotlinx.android.synthetic.main.fragment_customer_profile.view.*

/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */

class CustomerProfileFragment() : Fragment() {


    var customerController = CustomerController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_customer_profile, container, false)

        val customerUUID = arguments.getString(CUSTOMER_UUID)

        val customer: Customer = customerController.getCustomerBy(customerUUID)

        rootView.floor_no.text = customer.floorNo
        rootView.bed_no.text = customer.bedNo
        rootView.mobile_no.text = customer.mobileNo
        rootView.permit_no.text = customer.permitNo

        return rootView
    }


    companion object {
        private val CUSTOMER_UUID = "UUID"

        fun newInstance(customerUUID: String): CustomerProfileFragment {
            val fragment = CustomerProfileFragment()
            val args = Bundle()
            args.putString(CUSTOMER_UUID, customerUUID)
            fragment.arguments = args
            return fragment
        }
    }
}