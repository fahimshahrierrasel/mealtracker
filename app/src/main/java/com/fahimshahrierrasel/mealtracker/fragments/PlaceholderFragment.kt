package com.fahimshahrierrasel.mealtracker.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahimshahrierrasel.mealtracker.R
import kotlinx.android.synthetic.main.fragment_customer_details.view.*

/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */

class PlaceholderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_customer_details, container, false)
        rootView.section_label.text = arguments.getInt(ARG_SECTION_NUMBER).toString()
        return rootView
    }

    companion object {
        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            val fragment = PlaceholderFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}