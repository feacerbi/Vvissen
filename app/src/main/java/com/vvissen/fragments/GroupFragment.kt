package com.vvissen.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vvissen.R
import com.vvissen.adapters.GroupAdapter
import com.vvissen.model.Trip
import kotlinx.android.synthetic.main.fragment_group.view.*
import org.parceler.Parcels

/**
 * A placeholder fragment containing a simple view.
 */
class GroupFragment : Fragment() {

    private var trip: Trip? = null

    var groupAdapter: GroupAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            trip = Parcels.unwrap(arguments?.getParcelable(TRIP_EXTRA))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_group, container, false)

        if(view is NestedScrollView) {
            with(view) {
                groupAdapter = GroupAdapter(trip)
                rv_group_list.adapter = groupAdapter
            }
        }
        return view
    }

    companion object {
        val TRIP_EXTRA = "trip"

        fun newInstance(trip: Trip): GroupFragment {
            val fragment = GroupFragment()
            val args = Bundle()
            args.putParcelable(TRIP_EXTRA, Parcels.wrap(trip))
            fragment.arguments = args
            return fragment
        }
    }
}
