package com.vvissen.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.vvissen.fragments.HousesFragment
import com.vvissen.fragments.MyTripsFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        val MY_TRIPS_FRAGMENT = 0
        val HOUSES_FRAGMENT = 1
    }

    val myTripsFragment by lazy {
        MyTripsFragment()
    }

    val housesFragment by lazy {
        HousesFragment()
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            MY_TRIPS_FRAGMENT -> { return myTripsFragment
            }
            HOUSES_FRAGMENT -> { return housesFragment
            }
            else -> { return housesFragment
            }
        }
    }

    override fun getCount() = 2

}