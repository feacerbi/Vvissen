package com.vvissen.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vvissen.R
import kotlinx.android.synthetic.main.activity_my_trips.*

class MyTripsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_trips)
        setSupportActionBar(toolbar)

        title = "My Trips"
    }

}
