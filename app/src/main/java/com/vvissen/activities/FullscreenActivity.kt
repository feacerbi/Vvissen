package com.vvissen.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler
import com.squareup.picasso.Picasso
import com.vvissen.R
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    val photoTitle by lazy {
        intent.getStringExtra(TITLE_EXTRA)
    }

    val photo by lazy {
        intent.getIntExtra(PHOTO_EXTRA, R.drawable.cancun_front)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        title = photoTitle

        iv_photo.setOnTouchListener(ImageMatrixTouchHandler(this))

        Picasso.with(this)
                .load(photo)
                .fit()
                .centerInside()
                .into(iv_photo)
    }

    companion object {
        val TITLE_EXTRA = "title"
        val PHOTO_EXTRA = "photo"
    }
}
