package com.vvissen.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.vvissen.fragments.HousePhotoFragment
import com.vvissen.model.House

class HousePhotosPagerAdapter(fm: FragmentManager, val house: House) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return HousePhotoFragment.newInstance(house, position)
    }

    override fun getCount() = house.photos.size
}