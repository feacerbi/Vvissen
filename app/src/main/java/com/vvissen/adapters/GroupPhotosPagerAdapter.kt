package com.vvissen.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.vvissen.fragments.GroupPhotoFragment
import com.vvissen.model.User

class GroupPhotosPagerAdapter(fm: FragmentManager, val group: ArrayList<User>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return GroupPhotoFragment.newInstance(group)
    }

    override fun getCount() = 1
}