package com.vvissen.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import com.vvissen.R
import com.vvissen.adapters.HousePhotosPagerAdapter
import com.vvissen.adapters.ProfilePicturesAdapter
import com.vvissen.adapters.ReviewsAdapter
import com.vvissen.fragments.HousePhotoFragment
import com.vvissen.model.*
import com.vvissen.utils.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_house_details.*
import kotlinx.android.synthetic.main.reviews_list.view.*
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

    val house by lazy {
        Parcels.unwrap(intent.getParcelableExtra(HousePhotoFragment.HOUSE_EXTRA)) as House
    }

    val trip by lazy {
        if(house.packageTier.name == PackagePremium().name) Trip().createFakeTrip() else if(house.packageTier.name == PackageLuxury().name) Trip().createFakeTrip2() else Trip().createFakeTrip3()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_details)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapterHouse = HousePhotosPagerAdapter(supportFragmentManager, house)

        updateTripDates()
        trip.groupType = GroupRandom()

        setUpUI()
    }

    private fun setUpUI() {
        title = ""

        // Set up the ViewPager with the sections adapter.
        vp_photos.adapter = mSectionsPagerAdapterHouse

        vpi_photos_indicator.setViewPager(vp_photos)

        tv_place_name.text = house.place
        tv_package_tier.text = house.packageTier.name
        rb_house_rating.rating = house.rating
        tv_house_rating_grade.text = house.rating.toString()
        tv_rating_count.text = String.format("(%d)", house.ratingCount)
        tv_house_info.text = house.description
        tv_groups_info.text = GroupRandom().info

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

        iv_house_map_icon.setOnClickListener {
            openMap()
        }

        calcPeriodAndPrice()

        cl_trip_type_random_button.setOnClickListener {
            trip.groupType = GroupRandom()
            selectTripType(true, cl_trip_type_random_button, GroupRandom())
            selectTripType(false, cl_trip_type_friends_button, GroupFriends())
            selectTripType(false, cl_trip_type_groups_button, GroupGroups())

            tv_confirmed_title.visibility = View.VISIBLE
            rv_profiles_list.visibility = View.VISIBLE
            v_separator2.visibility = View.VISIBLE

            rv_profiles_list.adapter = ProfilePicturesAdapter(arrayOf(
                    R.drawable.brunette,
                    R.drawable.brunette2,
                    R.drawable.brunette4,
                    R.drawable.brunette5,
                    R.drawable.blonde,
                    R.drawable.blonde2,
                    R.drawable.red,
                    R.drawable.japa))

            bt_start_match.text = "Start Match"
            bt_start_match.setOnClickListener {
                openMatcher()
            }
        }

        cl_trip_type_groups_button.setOnClickListener {
            trip.groupType = GroupGroups()
            selectTripType(false, cl_trip_type_random_button, GroupRandom())
            selectTripType(false, cl_trip_type_friends_button, GroupFriends())
            selectTripType(true, cl_trip_type_groups_button, GroupGroups())

            tv_confirmed_title.visibility = View.VISIBLE
            rv_profiles_list.visibility = View.VISIBLE
            v_separator2.visibility = View.VISIBLE

            rv_profiles_list.adapter = ProfilePicturesAdapter(arrayOf(
                    R.drawable.blonde,
                    R.drawable.blonde2,
                    R.drawable.red,
                    R.drawable.japa,
                    R.drawable.brunette,
                    R.drawable.brunette2,
                    R.drawable.brunette4,
                    R.drawable.brunette5,
                    R.drawable.brunette6))

            bt_start_match.text = "Invite Group"
            bt_start_match.setOnClickListener {
                openInvite()
            }
        }

        cl_trip_type_friends_button.setOnClickListener {
            trip.groupType = GroupFriends()
            selectTripType(false, cl_trip_type_random_button, GroupRandom())
            selectTripType(true, cl_trip_type_friends_button, GroupFriends())
            selectTripType(false, cl_trip_type_groups_button, GroupGroups())

            tv_confirmed_title.visibility = View.GONE
            rv_profiles_list.visibility = View.GONE
            v_separator2.visibility = View.GONE

            bt_start_match.text = "Invite Friends"
            bt_start_match.setOnClickListener {
                openInvite()
            }
        }

        bt_start_match.setOnClickListener {
            openMatcher()
        }

        tv_more_info.setOnClickListener {
            openMoreInfo()
        }

        fl_house_rating.setOnClickListener {
            openReviews()
        }
    }

    fun openMatcher() {
        launchActivityWithExtras<MatchActivity>(
                MatchActivity::class,
                arrayOf(MatchActivity.TRIP_EXTRA),
                arrayOf(trip),
                false)
    }

    fun openInvite() {
        launchActivityWithExtras<InviteActivity>(
                InviteActivity::class,
                arrayOf(MatchActivity.TRIP_EXTRA),
                arrayOf(trip),
                false)
    }

    fun openMoreInfo() {
        AlertDialog.Builder(this).showTextDialog("All Inclusive", getString(R.string.text_all_included))
    }

    fun openReviews() {
        val listView = layoutInflater.inflate(R.layout.reviews_list, null)

        if(listView is RecyclerView) {
            listView.rv_reviews_list.adapter = ReviewsAdapter(arrayOf(Review().createFakeReview(), Review().createFakeReview2(), Review().createFakeReview3()))
        }

        AlertDialog.Builder(this)
                .setTitle("Reviews")
                .setView(listView)
                .setPositiveButton("OK", { _, _ -> })
                .show()
    }

    private fun openMap() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo:0,0?q=" + house.address)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun pickCheckinDate() {
        dateButton = CHECKIN_BUTTON
        val picker = DatePickerDialog.newInstance(this, checkinDate.get(Calendar.YEAR), checkinDate.get(Calendar.MONTH), checkinDate.get(Calendar.DAY_OF_MONTH))
        picker.minDate = Calendar.getInstance()

        val acceptedDate = Calendar.getInstance()
        acceptedDate.rollDays(7)

        picker.selectableDays = arrayOf(acceptedDate)
        picker.show(fragmentManager, "datePicker")
    }

    fun pickCheckoutDate() {
        dateButton = CHECKOUT_BUTTON
        val picker = DatePickerDialog.newInstance(this, checkoutDate.get(Calendar.YEAR), checkoutDate.get(Calendar.MONTH), checkoutDate.get(Calendar.DAY_OF_MONTH))

        val minDate = Calendar.getInstance()
        minDate.timeInMillis = checkinDate.timeInMillis
        minDate.rollDays(2)

        val acceptedDate = Calendar.getInstance()
        acceptedDate.rollDays(8)
        val acceptedDate2 = Calendar.getInstance()
        acceptedDate2.rollDays(9)

        picker.minDate = minDate
        picker.selectableDays = arrayOf(acceptedDate, acceptedDate2)
        picker.show(fragmentManager, "datePicker")
    }

    override fun onDateSet(view: com.wdullaer.materialdatetimepicker.date.DatePickerDialog?, year: Int, month: Int, day: Int) {
        if(dateButton == CHECKIN_BUTTON) {
            checkinDate.set(year, month, day)

            if(checkinDate >= checkoutDate) {
                checkoutDate = Calendar.getInstance()
                checkoutDate.timeInMillis = checkinDate.timeInMillis
                checkoutDate.rollDays(trip.totalDays())
            }
        } else if (dateButton == CHECKOUT_BUTTON){
            checkoutDate.set(year, month, day)
        }

        updateTripDates()
        calcPeriodAndPrice()
    }

    fun updateTripDates() {
        trip.period = Pair(checkinDate.timeInMillis, checkoutDate.timeInMillis)
    }

    fun calcPeriodAndPrice() {
        tv_checkin_date_day.text = String.format("%2d", checkinDate.get(Calendar.DAY_OF_MONTH))
        tv_checkin_date_month.text = checkinDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

        tv_checkout_date_day.text = String.format("%2d", checkoutDate.get(Calendar.DAY_OF_MONTH))
        tv_checkout_date_month.text = checkoutDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

        tv_house_price.text = trip.totalPrice().toCurrency()
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
        if(select) tv_groups_info.text = type.info

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
