package com.vvissen.adapters.listeners

import android.support.v7.app.AppCompatActivity

interface ExtraPhotoClickListener {
    fun onPhotoChosen(photo: Int)
    fun getActivity(): AppCompatActivity
}