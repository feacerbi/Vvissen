package com.vvissen.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.vvissen.fragments.GroupFragment
import com.vvissen.fragments.TripInfoFragment
import com.vvissen.model.Trip

class TripInfoPagerAdapter(fm: FragmentManager, trip: Trip) : FragmentPagerAdapter(fm) {

    companion object {
        val TRIP_INFO_FRAGMENT = 0
        val TRIP_GROUP_FRAGMENT = 1
    }

    val tripInfoFragment by lazy {
        TripInfoFragment.newInstance(trip)
    }

    val groupFragment by lazy {
        GroupFragment.newInstance(trip)
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            TRIP_INFO_FRAGMENT -> { return tripInfoFragment
            }
            TRIP_GROUP_FRAGMENT -> { return groupFragment
            }
            else -> { return tripInfoFragment
            }
        }
    }

    override fun getCount() = 2

}