package com.vvissen.fragments

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vvissen.R
import com.vvissen.adapters.HousesAdapter
import com.vvissen.adapters.listeners.HouseListClickListener
import com.vvissen.drawer.DrawerController
import com.vvissen.drawer.DrawerItem
import com.vvissen.drawer.DrawerListener
import com.vvissen.model.House
import com.vvissen.model.PackageLuxury
import com.vvissen.model.PackagePremium
import com.vvissen.model.PackageVip
import com.vvissen.utils.PagerController
import kotlinx.android.synthetic.main.fragment_houses.*
import kotlinx.android.synthetic.main.fragment_houses.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class HousesFragment : Fragment(), DrawerListener {

    companion object {
        val VIP_TIER_PACKAGE = Pair(0, PackageVip().name)
        val LUXURY_TIER_PACKAGE = Pair(1, PackageLuxury().name)
        val PREMIUM_TIER_PACKAGE = Pair(2, PackagePremium().name)
        val FAVORITES = 3

        val COUNTRY = 0
        val CITY = 1
        val ORDER = 2
        val ORDER_TYPE = 3
    }

    var mListener: PagerController? = null
    val selectedPackages = arrayOf(true, true, true, false)
    var selectedSpinners = arrayOf(0, 0, 0, 0)

    var housesAdapter: HousesAdapter? = null

    val houseList by lazy {
        arrayListOf(
                House().createFakeHouse(),
                House().createFakeHouse2(),
                House().createFakeHouse3())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_houses, container, false)

        if(view is ConstraintLayout) {
            with(view) {
                housesAdapter = HousesAdapter(mListener as HouseListClickListener, houseList.toTypedArray())
                rv_houses_list.adapter = housesAdapter

                toolbar_houses.setNavigationOnClickListener { mListener?.setPage(0) }
                toolbar_houses.inflateMenu(R.menu.menu_houses)

                toolbar_houses.setOnMenuItemClickListener( { _ ->
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
                selectedSpinners[COUNTRY] = pair.second
            }
            DrawerItem.CITIES -> {
                selectedSpinners[CITY] = pair.second
            }
            DrawerItem.PREMIUM -> {
                selectedPackages[PREMIUM_TIER_PACKAGE.first] = pair.second != 0
            }
            DrawerItem.LUXURY -> {
                selectedPackages[LUXURY_TIER_PACKAGE.first] = pair.second != 0
            }
            DrawerItem.VIP -> {
                selectedPackages[VIP_TIER_PACKAGE.first] = pair.second != 0
            }
            DrawerItem.FAVORITES -> {
                selectedPackages[FAVORITES] = pair.second != 0
            }
            DrawerItem.ORDER -> {
                selectedSpinners[ORDER] = pair.second
            }
            DrawerItem.ORDERTYPE -> {
                selectedSpinners[ORDER_TYPE] = pair.second
            }
        }

        applyFilters()
    }

    private fun applyFilters() {
        val citiesArray = when (selectedSpinners[COUNTRY]) {
            0 -> mListener?.getActivity()?.resources?.getStringArray(R.array.all_cities)
            1 -> mListener?.getActivity()?.resources?.getStringArray(R.array.brazil_cities)
            2 -> mListener?.getActivity()?.resources?.getStringArray(R.array.mexico_cities)
            else -> mListener?.getActivity()?.resources?.getStringArray(R.array.all_cities)
        }

        val filteredList = mutableListOf<House>()

        if(selectedPackages[FAVORITES]) {
            filteredList.addAll(
                    houseList.filter {
                        it.favorite
                    }
            )
        } else {
            filteredList.addAll(
                    houseList.filter {
                        (it.packageTier.name == VIP_TIER_PACKAGE.second && selectedPackages[VIP_TIER_PACKAGE.first] ||
                                it.packageTier.name == LUXURY_TIER_PACKAGE.second && selectedPackages[LUXURY_TIER_PACKAGE.first] ||
                                it.packageTier.name == PREMIUM_TIER_PACKAGE.second && selectedPackages[PREMIUM_TIER_PACKAGE.first])
                                &&
                                (it.place == citiesArray!![selectedSpinners[CITY]].dropLast(5) || citiesArray[selectedSpinners[CITY]] == "All")
                    }
            )
        }

        if (selectedSpinners[ORDER_TYPE] == 0)
            filteredList.sortBy { it.name } else
            filteredList.sortBy { it.price }

        if (selectedSpinners[ORDER] == 1) filteredList.reverse()

        housesAdapter?.setItems(filteredList.toTypedArray())

        tv_empty.visibility = if(filteredList.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun clearFilters() {
        selectedPackages[VIP_TIER_PACKAGE.first] = true
        selectedPackages[LUXURY_TIER_PACKAGE.first] = true
        selectedPackages[PREMIUM_TIER_PACKAGE.first] = true
        selectedPackages[FAVORITES] = false

        selectedSpinners[COUNTRY] = 0
        selectedSpinners[CITY] = 0
        selectedSpinners[ORDER] = 0
        selectedSpinners[ORDER_TYPE] = 0

        applyFilters()
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
