package com.vvissen.activities

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.widget.DatePicker
import com.vvissen.R
import com.vvissen.adapters.HousePhotosPagerAdapter
import com.vvissen.adapters.ProfilePicturesAdapter
import com.vvissen.fragments.HousePhotoFragment
import com.vvissen.launchActivity
import com.vvissen.model.*
import com.vvissen.noDecimals
import com.vvissen.rollDays
import kotlinx.android.synthetic.main.activity_house_details.*
import kotlinx.android.synthetic.main.start_match_toolbar.*
import kotlinx.android.synthetic.main.trip_type_friends_list_item.*
import kotlinx.android.synthetic.main.trip_type_groups_list_item.*
import kotlinx.android.synthetic.main.trip_type_random_list_item.*
import org.parceler.Parcels
import java.text.NumberFormat
import java.util.*

class HouseDetailsActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    companion object {
        val CHECKIN_BUTTON = 0
        val CHECKOUT_BUTTON = 1
        val RANDOM_BUTTON = 0
        val GROUPS_BUTTON = 1
        val FRIENDS_BUTTON = 2
    }

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapterHouse: HousePhotosPagerAdapter? = null
    var dateButton = CHECKIN_BUTTON
    var checkinDate = Calendar.getInstance(Locale.getDefault())
    var checkoutDate = Calendar.getInstance().rollDays(2)
    var period = 3
    var tripType = RANDOM_BUTTON

    val house by lazy {
        Parcels.unwrap(intent.getParcelableExtra(HousePhotoFragment.HOUSE_EXTRA)) as House
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_details)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        title = house.name
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapterHouse = HousePhotosPagerAdapter(supportFragmentManager, house)

        setUpUI()
    }

    private fun setUpUI() {
        // Set up the ViewPager with the sections adapter.
        vp_photos.adapter = mSectionsPagerAdapterHouse

        vpi_photos_indicator.setViewPager(vp_photos)

        tv_place_name.text = house.place
        tv_package_tier.text = house.packageTier.name
        rb_house_rating.rating = house.rating
        tv_house_rating_grade.text = house.rating.toString()
        tv_rating_count.text = String.format("(%d)", house.ratingCount)
        tv_house_info.text = house.description

        rv_profiles_list.adapter = ProfilePicturesAdapter(arrayOf(
                R.drawable.brunette,
                R.drawable.brunette2,
                R.drawable.brunette4,
                R.drawable.brunette5,
                R.drawable.brunette6,
                R.drawable.blonde,
                R.drawable.blonde2,
                R.drawable.red,
                R.drawable.japa))

        tv_checkin_date_day.setOnClickListener {
            pickCheckinDate()
        }
        tv_checkin_date_month.setOnClickListener {
            pickCheckinDate()
        }

        tv_checkout_date_day.setOnClickListener {
            pickCheckoutDate()
        }
        tv_checkout_date_month.setOnClickListener {
            pickCheckoutDate()
        }

        tv_house_address.text = house.address
        tv_house_price_rate.text = String.format("U${NumberFormat.getCurrencyInstance(Locale.getDefault()).format(house.price).noDecimals()}")

        calcPeriodAndPrice()

        cl_trip_type_random_button.setOnClickListener {
            tripType = RANDOM_BUTTON
            selectTripType(true, cl_trip_type_random_button, GroupRandom())
            selectTripType(false, cl_trip_type_groups_button, GroupGroups())
            selectTripType(false, cl_trip_type_friends_button, GroupFriends())
        }
        cl_trip_type_groups_button.setOnClickListener {
            tripType = GROUPS_BUTTON
            selectTripType(true, cl_trip_type_groups_button, GroupGroups())
            selectTripType(false, cl_trip_type_random_button, GroupRandom())
            selectTripType(false, cl_trip_type_friends_button, GroupFriends())
        }
        cl_trip_type_friends_button.setOnClickListener {
            tripType = FRIENDS_BUTTON
            selectTripType(true, cl_trip_type_friends_button, GroupFriends())
            selectTripType(false, cl_trip_type_random_button, GroupRandom())
            selectTripType(false, cl_trip_type_groups_button, GroupGroups())
        }

        bt_start_match.setOnClickListener {
            launchActivity(MatchActivity::class)
        }
    }

    fun pickCheckinDate() {
        dateButton = CHECKIN_BUTTON
        val picker = DatePickerDialog(this, this, checkinDate.get(Calendar.YEAR), checkinDate.get(Calendar.MONTH), checkinDate.get(Calendar.DAY_OF_MONTH))
        picker.datePicker.minDate = Calendar.getInstance().timeInMillis
        picker.show()
    }

    fun pickCheckoutDate() {
        dateButton = CHECKOUT_BUTTON
        val picker = DatePickerDialog(this, this, checkoutDate.get(Calendar.YEAR), checkoutDate.get(Calendar.MONTH), checkoutDate.get(Calendar.DAY_OF_MONTH))

        val minDate = Calendar.getInstance()
        minDate.timeInMillis = checkinDate.timeInMillis
        minDate.rollDays(2)

        picker.datePicker.minDate = minDate.timeInMillis
        picker.show()
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        if(dateButton == CHECKIN_BUTTON) {
            checkinDate.set(year, month, day)

            if(checkinDate >= checkoutDate) {
                checkoutDate = Calendar.getInstance()
                checkoutDate.timeInMillis = checkinDate.timeInMillis
                checkoutDate.rollDays(period - 1)
            }
        } else if (dateButton == CHECKOUT_BUTTON){
            checkoutDate.set(year, month, day)
        }

        calcPeriodAndPrice()
    }

    fun calcPeriodAndPrice() {
        tv_checkin_date_day.text = String.format("%2d", checkinDate.get(Calendar.DAY_OF_MONTH))
        tv_checkin_date_month.text = checkinDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

        tv_checkout_date_day.text = String.format("%2d", checkoutDate.get(Calendar.DAY_OF_MONTH))
        tv_checkout_date_month.text = checkoutDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

        period = checkoutDate.get(Calendar.DAY_OF_MONTH) - checkinDate.get(Calendar.DAY_OF_MONTH) + 1
        val total = house.price / 3 * period

        tv_house_price.text = String.format("U${NumberFormat.getCurrencyInstance(Locale.getDefault()).format(total).noDecimals()}")
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_house_details, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.itemId
//
//        if (id == R.id.action_settings) {
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    fun selectTripType(select: Boolean, button: ConstraintLayout, type: GroupType) {
        button.setBackgroundResource(if(select) R.drawable.shape_outline_accent else R.drawable.shape_outline_white)

        when(type) {
            is GroupRandom -> {
                tv_type_random_title.setTextColor(if (select) resources.getColor(R.color.colorAccent) else resources.getColor(android.R.color.white))
                iv_type_random_icon.imageTintList = if (select) ColorStateList.valueOf(resources.getColor(R.color.colorAccent)) else ColorStateList.valueOf(resources.getColor(android.R.color.white))
            }
            is GroupGroups -> {
                tv_type_groups_title.setTextColor(if (select) resources.getColor(R.color.colorAccent) else resources.getColor(android.R.color.white))
                iv_type_groups_icon.imageTintList = if (select) ColorStateList.valueOf(resources.getColor(R.color.colorAccent)) else ColorStateList.valueOf(resources.getColor(android.R.color.white))
                iv_type_groups_icon2.imageTintList = if (select) ColorStateList.valueOf(resources.getColor(R.color.colorAccent)) else ColorStateList.valueOf(resources.getColor(android.R.color.white))
            }
            is GroupFriends -> {
                tv_type_friends_title.setTextColor(if (select) resources.getColor(R.color.colorAccent) else resources.getColor(android.R.color.white))
                iv_type_friends_icon.imageTintList = if (select) ColorStateList.valueOf(resources.getColor(R.color.colorAccent)) else ColorStateList.valueOf(resources.getColor(android.R.color.white))
            }
        }
    }

}
