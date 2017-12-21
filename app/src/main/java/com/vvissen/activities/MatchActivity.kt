package com.vvissen.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.vvissen.R
import com.vvissen.adapters.UserPhotosPagerAdapter
import com.vvissen.model.User
import kotlinx.android.synthetic.main.activity_match.*

class MatchActivity : AppCompatActivity() {

    private var mSectionsPagerAdapterHouse: UserPhotosPagerAdapter? = null

    val users: Array<User> by lazy {
        arrayOf(
                User().createFakeUser(),
                User().createFakeUser2(),
                User().createFakeUser3(),
                User().createFakeUser4(),
                User().createFakeUser5())
    }

    var currentUser = users[0]
    var newTrip = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)
        setSupportActionBar(toolbar)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.title =  currentUser.name
        toolbar.subtitle = String.format("%d years", currentUser.age)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapterHouse = UserPhotosPagerAdapter(supportFragmentManager, currentUser)

        setUpUI()
    }

    private fun setUpUI() {
        vp_photos.adapter = mSectionsPagerAdapterHouse

        vpi_photos_indicator.setViewPager(vp_photos)

        fab_accept.setOnClickListener { view ->
            nextUser()

            if(newTrip) {
                Toast.makeText(this, "New trip added as Pending", Toast.LENGTH_SHORT).show()
                newTrip = false
            }
        }
        fab_deny.setOnClickListener { view ->
            nextUser()
        }
    }

    fun nextUser() {
        val nextUser = users.indexOf(currentUser) + 1

        if(nextUser < users.size) {
            currentUser = users[nextUser]

            toolbar.title = currentUser.name
            toolbar.subtitle = String.format("%d years", currentUser.age)

            mSectionsPagerAdapterHouse = UserPhotosPagerAdapter(supportFragmentManager, currentUser)
            vp_photos.adapter = mSectionsPagerAdapterHouse
            vpi_photos_indicator.setViewPager(vp_photos)
        } else {
            onBackPressed()
        }
    }
}
