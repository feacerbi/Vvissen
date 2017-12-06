package com.vvissen

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed({
            launchActivity(MainActivity::class)
            finish()
        }, 2000)
    }
}
