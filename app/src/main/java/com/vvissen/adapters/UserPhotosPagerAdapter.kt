package com.vvissen.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.vvissen.fragments.UserPhotoFragment
import com.vvissen.model.User

class UserPhotosPagerAdapter(fm: FragmentManager, val user: User) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return UserPhotoFragment.newInstance(user, position)
    }

    override fun getCount() = user.photos.size
}