package com.vvissen

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vvissen.model.Trip
import kotlinx.android.synthetic.main.fragment_my_trips.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class MyTripsFragment : Fragment() {

    private var mListener: PagerController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_my_trips, container, false)

        val tripsAdapter = TripsAdapter(arrayListOf(
                Trip().createFakeTrip(),
                Trip().createFakeTrip2(),
                Trip().createFakeTrip3()))

        if(view is ConstraintLayout) {
            with(view) {
                rv_trips_list.adapter = tripsAdapter

                toolbar_my_trips.inflateMenu(R.menu.menu_my_trips)
                toolbar_my_trips.setOnMenuItemClickListener( { item ->
                    if(item.itemId == R.id.action_back) {
                        mListener?.setPage(1)
                    }
                    true
                })
            }
        }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is PagerController) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement PagerController")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

//    fun checkEmpty(adapter: TripsAdapter, trips: ArrayList<Trip>) {
//        rv_trips_list.visibility = if(trips.isEmpty()) View.INVISIBLE else View.VISIBLE
//        tv_empty.visibility = if(trips.isEmpty()) View.VISIBLE else View.INVISIBLE
//    }
}
