package com.vvissen

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        val MY_TRIPS_FRAGMENT = 0
        val HOUSES_FRAGMENT = 1
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            MY_TRIPS_FRAGMENT -> { return MyTripsFragment() }
            HOUSES_FRAGMENT -> { return HousesFragment() }
            else -> { return HousesFragment() }
        }
    }

    override fun getCount() = 2

}