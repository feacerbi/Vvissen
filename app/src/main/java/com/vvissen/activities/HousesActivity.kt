package com.vvissen.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vvissen.R
import kotlinx.android.synthetic.main.activity_houses.*

class HousesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_houses)
        setSupportActionBar(toolbar)

        title = resources.getString(R.string.app_name)
    }
}
