package com.vvissen.activities

import android.content.Context
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.vvissen.R
import com.vvissen.adapters.MainPagerAdapter
import com.vvissen.adapters.listeners.HouseListClickListener
import com.vvissen.adapters.listeners.TripListClickListener
import com.vvissen.drawer.DrawerController
import com.vvissen.drawer.DrawerItem
import com.vvissen.drawer.DrawerListener
import com.vvissen.fragments.HousePhotoFragment
import com.vvissen.model.*
import com.vvissen.utils.PagerController
import com.vvissen.utils.launchActivity
import com.vvissen.utils.launchActivityWithExtras
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PagerController, DrawerController, ViewPager.OnPageChangeListener, HouseListClickListener, TripListClickListener {

    companion object {
        val USER = "user"
    }

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    var mSectionsPagerAdapter: MainPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkNewUser()

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = MainPagerAdapter(supportFragmentManager)

        setUpFilters()

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
        container.addOnPageChangeListener(this)

        setPage(1)
    }

    fun checkNewUser() {
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if(!preferences.contains(USER) || preferences.getString(USER, "-1") != uid) {
            preferences.edit().putString(USER, uid).apply()
            launchActivity(NewUserActivity::class)
        }
    }

    private fun setUpFilters() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.syncState()

        drawer_layout.addDrawerListener(toggle)

        val citiesSpinner = nav_view.menu.findItem(R.id.nav_city).actionView as Spinner
        val citiesAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, resources.getStringArray(R.array.brazil_cities))
        citiesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        citiesSpinner.adapter = citiesAdapter
        citiesSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Nothing
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onFilterSelected(DrawerItem.CITIES, position)
            }
        }

        val countriesSpinner = nav_view.menu.findItem(R.id.nav_country).actionView as Spinner
        val countriesAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, resources.getStringArray(R.array.countries))
        countriesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        countriesSpinner.adapter = countriesAdapter
        countriesSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Nothing
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var newCitiesAdapter: ArrayAdapter<String>? = null

                when(position) {
                    0 -> newCitiesAdapter = ArrayAdapter(this@MainActivity, R.layout.spinner_item, resources.getStringArray(R.array.all_cities))
                    1 -> newCitiesAdapter = ArrayAdapter(this@MainActivity, R.layout.spinner_item, resources.getStringArray(R.array.brazil_cities))
                    2 -> newCitiesAdapter = ArrayAdapter(this@MainActivity, R.layout.spinner_item, resources.getStringArray(R.array.mexico_cities))
                }

                newCitiesAdapter?.setDropDownViewResource(R.layout.spinner_dropdown_item)
                citiesSpinner.adapter = newCitiesAdapter

                onFilterSelected(DrawerItem.COUNTRIES, position)
            }
        }

        val premiumBox = nav_view.menu.findItem(R.id.nav_premium).actionView as CheckBox
        premiumBox.text = PackagePremium().name
        premiumBox.isChecked = true
        premiumBox.setOnCheckedChangeListener({
            _, isChecked -> onFilterSelected(DrawerItem.PREMIUM, if(isChecked) 1 else 0)
        })

        val luxuryBox = nav_view.menu.findItem(R.id.nav_luxury).actionView as CheckBox
        luxuryBox.text = PackageLuxury().name
        luxuryBox.isChecked = true
        luxuryBox.setOnCheckedChangeListener({
            _, isChecked -> onFilterSelected(DrawerItem.LUXURY, if(isChecked) 1 else 0)
        })

        val vipBox = nav_view.menu.findItem(R.id.nav_vip).actionView as CheckBox
        vipBox.text = PackageVip().name
        vipBox.isChecked = true
        vipBox.setOnCheckedChangeListener({
            _, isChecked -> onFilterSelected(DrawerItem.VIP, if(isChecked) 1 else 0)
        })

        val favBox = nav_view.menu.findItem(R.id.nav_favorites).actionView as CheckBox
        favBox.text = "Favorites"
        favBox.setOnCheckedChangeListener({
            _, isChecked -> onFilterSelected(DrawerItem.FAVORITES, if(isChecked) 1 else 0)
        })

        val orderSpinner = nav_view.menu.findItem(R.id.nav_order).actionView as Spinner
        val orderTypeAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, resources.getStringArray(R.array.order_type))
        orderTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        orderSpinner.adapter = orderTypeAdapter
        orderSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Nothing
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onFilterSelected(DrawerItem.ORDER, position)
            }
        }

        val clearButton = nav_view.menu.findItem(R.id.nav_button_clear).actionView as Button
        clearButton.setOnClickListener {
            clearFilters()

            countriesSpinner.setSelection(0)
            premiumBox.isChecked = true
            luxuryBox.isChecked = true
            vipBox.isChecked = true
            orderSpinner.setSelection(0)
            favBox.isChecked = false
        }

        clearFilters()
    }

    override fun setPage(page: Int) {
        container.currentItem = page
    }

    override fun onPageScrollStateChanged(state: Int) {
        // Nothing
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // Nothing
    }

    override fun onPageSelected(position: Int) {
            drawer_layout.setDrawerLockMode(
                    if(container.currentItem == 1)
                        DrawerLayout.LOCK_MODE_UNLOCKED
                    else
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun clearFilters() {
        val currentFrag = mSectionsPagerAdapter?.getItem(container.currentItem)
        if(currentFrag is DrawerListener) {
            currentFrag.clearFilters()
        }
    }

    fun onFilterSelected(item: DrawerItem, value: Int) {
        val currentFrag = mSectionsPagerAdapter?.getItem(container.currentItem)
        if(currentFrag is DrawerListener) {
            currentFrag.onFilterSelected(Pair(item, value))
        }
    }

    override fun onHouseClicked(house: House) {
        launchActivityWithExtras<HouseDetailsActivity>(
                HouseDetailsActivity::class,
                arrayOf(HousePhotoFragment.HOUSE_EXTRA),
                arrayOf(house),
                false)
    }

    override fun onTripClicked(trip: Trip) {
        launchActivityWithExtras<TripActivity>(
                TripActivity::class,
                arrayOf(TripActivity.TRIP_EXTRA),
                arrayOf(trip),
                false)
    }

    override fun openDrawer() {
        drawer_layout.openDrawer(GravityCompat.END)
    }

    override fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.END)
    }

    override fun toggleDrawer() {
        if(drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END)
        } else {
            drawer_layout.openDrawer(GravityCompat.END)
        }
    }

    override fun isDrawerOpened() = drawer_layout.isDrawerOpen(GravityCompat.END)

    override fun getActivity(): AppCompatActivity {
        return this
    }

    override fun onBackPressed() {
        if (container.currentItem == 1 && drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END)
        } else if(container.currentItem == 0) {
            setPage(1)
        } else {
            finish()
        }
    }
}