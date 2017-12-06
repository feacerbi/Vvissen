package com.vvissen

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_local_select.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class SelectPlaceFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_local_select, container, false)

        if(view is ConstraintLayout) {
            with(view) {
                val countryAdapter = ArrayAdapter<String>(activity, R.layout.spinner_item, resources.getStringArray(R.array.countries))
                countryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                sp_country.adapter  = countryAdapter

                val cityAdapter = ArrayAdapter<String>(activity, R.layout.spinner_item, resources.getStringArray(R.array.brazil_cities))
                cityAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                sp_city.adapter = cityAdapter

                val packageAdapter = ArrayAdapter<String>(activity, R.layout.spinner_item, resources.getStringArray(R.array.packages))
                packageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                sp_package.adapter = packageAdapter
            }
        }

        return view
    }

//    fun checkEmpty(adapter: TripsAdapter, trips: ArrayList<Trip>) {
//        rv_trips_list.visibility = if(trips.isEmpty()) View.INVISIBLE else View.VISIBLE
//        tv_empty.visibility = if(trips.isEmpty()) View.VISIBLE else View.INVISIBLE
//    }
}
