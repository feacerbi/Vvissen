package com.vvissen.utils

import android.support.v7.app.AppCompatActivity

interface PagerController {
    fun setPage(page: Int)
    fun getActivity(): AppCompatActivity
}