package com.vvissen

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vvissen.model.House
import kotlinx.android.synthetic.main.fragment_houses.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class HousesFragment : Fragment(), DrawerListener {

    private var mListener: PagerController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_houses, container, false)

        val housesAdapter = HousesAdapter(arrayListOf(
                House().createFakeHouse(),
                House().createFakeHouse2(),
                House().createFakeHouse3(),
                House().createFakeHouse2(),
                House().createFakeHouse()))

        if(view is ConstraintLayout) {
            with(view) {
                rv_houses_list.adapter = housesAdapter

                toolbar_houses.setNavigationOnClickListener { mListener?.setPage(0) }
                toolbar_houses.inflateMenu(R.menu.menu_houses)

                toolbar_houses.setOnMenuItemClickListener( { item ->
                    if(mListener is DrawerController) {
                        val drawerController = mListener
                        (drawerController as DrawerController).toggleDrawer()
                    }
                    true
                } )
            }
        }

        return view
    }

    override fun onFilterSelected(pair: Pair<DrawerItem, Int>) {
        when (pair.first) {
            DrawerItem.COUNTRIES -> {
                // Nothing
            }
            DrawerItem.CITIES -> {
                Log.d("Houses", "Cities!")
            }
            DrawerItem.PREMIUM -> {
                Log.d("Houses", "Premium!")
            }
            DrawerItem.LUXURY -> {
                Log.d("Houses", "Luxury!")
            }
            DrawerItem.VIP -> {
                Log.d("Houses", "VIP!")
            }
        }
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
