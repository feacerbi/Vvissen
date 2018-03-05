package com.vvissen.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import com.google.firebase.auth.FirebaseAuth
import com.vvissen.R
import com.vvissen.adapters.SpinnerItemsAdapter
import com.vvissen.utils.backYears
import com.vvissen.utils.toBirthDate
import kotlinx.android.synthetic.main.activity_new_user.*
import kotlinx.android.synthetic.main.interest_box.view.*
import java.util.*

class NewUserActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    companion object {
        val EDIT_EXTRA = "edit"
    }

    var birthDate = Calendar.getInstance().backYears(18)

    val edit by lazy {
        intent.getBooleanExtra(EDIT_EXTRA, false)
    }

    val interests = Array(10, { _ -> false })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        setUpUI()
    }

    private fun setUpUI() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if(edit) {
            title = "Edit Profile"
            tv_welcome.visibility = View.GONE
            tv_welcome_description.text = "The more your profile represents your personality, more people will be interested."
        } else {
            title = ""
            tv_welcome.visibility = View.VISIBLE
            tv_welcome.text = String.format("Hi %s,", currentUser?.displayName)
        }

        tv_birth.text = birthDate.timeInMillis.toBirthDate()
        tv_birth.setOnClickListener { pickBirthDate() }

        val genderAdapter = SpinnerItemsAdapter(this, R.layout.profile_spinner_item, resources.getStringArray(R.array.genders), false)
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        val interestAdapter = SpinnerItemsAdapter(this, R.layout.profile_spinner_item, resources.getStringArray(R.array.interests), false)
        interestAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        val countryAdapter = SpinnerItemsAdapter(this, R.layout.profile_spinner_item, resources.getStringArray(R.array.from_countries), false)
        countryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        val cityAdapter = SpinnerItemsAdapter(this, R.layout.profile_spinner_item, resources.getStringArray(R.array.from_brazil_cities), false)
        cityAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        sp_gender.adapter = genderAdapter
        sp_interest.adapter = interestAdapter
        sp_country.adapter = countryAdapter
        sp_city.adapter = cityAdapter

        sp_country.onItemSelectedListener = this

        interest1.setOnClickListener { selectInterest(it, 0) }
        interest1.tv_interest_title.text = "music"
        interest2.setOnClickListener { selectInterest(it, 1) }
        interest2.tv_interest_title.text = "skate"
        interest3.setOnClickListener { selectInterest(it, 2) }
        interest3.tv_interest_title.text = "swimming"
        interest4.setOnClickListener { selectInterest(it, 3) }
        interest4.tv_interest_title.text = "games"
        interest5.setOnClickListener { selectInterest(it, 4) }
        interest5.tv_interest_title.text = "drinks"
        interest6.setOnClickListener { selectInterest(it, 5) }
        interest6.tv_interest_title.text = "bar"
        interest7.setOnClickListener { selectInterest(it, 6) }
        interest7.tv_interest_title.text = "dance"
        interest8.setOnClickListener { selectInterest(it, 7) }
        interest8.tv_interest_title.text = "bike"
        interest9.setOnClickListener { selectInterest(it, 8) }
        interest9.tv_interest_title.text = "travel"
        interest10.setOnClickListener { selectInterest(it, 9) }

        bt_ok.setOnClickListener {
            onBackPressed()
        }
        bt_cancel.setOnClickListener {
            onBackPressed()
        }

        et_about.setOnTouchListener {
            view, _ ->
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            false
        }
    }

    fun shouldSelect() = selectedNumber() < 3

    fun selectedNumber(): Int {
        var number = 0
        interests.forEach { if(it) number++ }
        return number
    }

    fun selectInterest(button: View, position: Int) {
        val isSelected = interests[position]

        if(isSelected || !isSelected && shouldSelect()) {
            button.tv_interest_title.setTextColor(if (isSelected) resources.getColor(android.R.color.white) else resources.getColor(R.color.colorAccent))
            button.setBackgroundResource(if (isSelected) R.drawable.shape_outline_white else R.drawable.shape_outline_accent)
            interests[position] = !isSelected

            tv_profile_interests_title.text = String.format("personality (%d)", 3 - selectedNumber())
            bt_ok.isEnabled = selectedNumber() == 3
        }
    }

    fun pickBirthDate() {
        val picker = DatePickerDialog(this, this, birthDate.get(Calendar.YEAR), birthDate.get(Calendar.MONTH), birthDate.get(Calendar.DAY_OF_MONTH))
        picker.datePicker.maxDate = Calendar.getInstance().backYears(18).timeInMillis
        picker.show()
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        birthDate.set(year, month, day)
        tv_birth.text = birthDate.timeInMillis.toBirthDate()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // Do nothing
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val adapter = parent?.adapter
        if(adapter is SpinnerItemsAdapter) {
            when(position) {
                0 -> {
                    val cityAdapter = SpinnerItemsAdapter(this, R.layout.profile_spinner_item, resources.getStringArray(R.array.from_brazil_cities), false)
                    sp_city.adapter = cityAdapter
                }
                1 -> {
                    val cityAdapter = SpinnerItemsAdapter(this, R.layout.profile_spinner_item, resources.getStringArray(R.array.from_mexico_cities), false)
                    sp_city.adapter = cityAdapter
                }
            }
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }
}
