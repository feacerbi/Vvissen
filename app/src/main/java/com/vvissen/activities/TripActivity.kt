package com.vvissen.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.vvissen.R
import com.vvissen.adapters.TripInfoPagerAdapter
import com.vvissen.fragments.HousePhotoFragment
import com.vvissen.model.PackageLuxury
import com.vvissen.model.PackagePremium
import com.vvissen.model.Trip
import com.vvissen.utils.launchActivityWithExtras
import com.vvissen.utils.showOneChoiceCancelableDialog
import kotlinx.android.synthetic.main.activity_trip.*
import org.parceler.Parcels

class TripActivity : AppCompatActivity() {

    companion object {
        val TRIP_EXTRA = "trip"
    }

    val trip by lazy {
        Parcels.unwrap(intent.getParcelableExtra(TRIP_EXTRA)) as Trip
    }

    var confirmed = false

    private var mSectionsPagerAdapter: TripInfoPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = TripInfoPagerAdapter(supportFragmentManager, trip)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tl_tabs))
        tl_tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        setUpUI()
    }

    private fun setUpUI() {
        title = ""

        tv_place.text = trip.house.place
        tv_package_tier.text = trip.house.packageTier.name

        val photo = if(trip.house.packageTier.name == PackagePremium().name) R.drawable.cancun else if(trip.house.packageTier.name == PackageLuxury().name) R.drawable.rio else R.drawable.maresias

        Picasso.with(this)
                .load("error")
                .fit()
                .centerCrop()
                .placeholder(photo)
                .error(photo)
                .into(iv_place_photo)

        iv_house_button.setOnClickListener {
            launchActivityWithExtras<HouseDetailsActivity>(
                    HouseDetailsActivity::class,
                    arrayOf(HousePhotoFragment.HOUSE_EXTRA),
                    arrayOf(trip.house),
                    false)
        }

        if(trip.isReady()) {
            fab.hide()
        } else {
            fab.show()

            if(trip.isConfirmed()) {
                fab.setImageResource(if(confirmed) R.drawable.ic_close_white_24dp else R.drawable.ic_check_white_24dp)
            } else {
                fab.setImageResource(R.drawable.ic_favorite_white_24dp)
            }
        }

        fab.setOnClickListener { view ->
            if(trip.isConfirmed()) {
                confirmed = !confirmed
                fab.setImageResource(if (confirmed) R.drawable.ic_close_white_24dp else R.drawable.ic_check_white_24dp)
                Snackbar.make(view, if (confirmed) "Trip confirmed" else "Trip canceled", Snackbar.LENGTH_LONG).show()
            } else {
                openMatcher()
            }
        }
    }

    fun openMatcher() {
        launchActivityWithExtras<MatchActivity>(
                MatchActivity::class,
                arrayOf(MatchActivity.TRIP_EXTRA, MatchActivity.TRIP_NEW),
                arrayOf(trip, false),
                false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        return if(trip.isReady()) false else {
            menuInflater.inflate(R.menu.menu_trip, menu)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_exit) {
            AlertDialog.Builder(this).showOneChoiceCancelableDialog("Exit Trip", "Do you want to exit and remove this trip?", "Yes", {
                _, _ ->
                Toast.makeText(this, "Trip removed", Toast.LENGTH_SHORT).show()
                finish()
            })
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
