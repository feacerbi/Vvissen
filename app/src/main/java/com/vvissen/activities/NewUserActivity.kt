package com.vvissen.activities

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
import java.util.*

class NewUserActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    companion object {
        val EDIT_EXTRA = "edit"
    }

    var birthDate = Calendar.getInstance().backYears(18)

    val edit by lazy {
        intent.getBooleanExtra(EDIT_EXTRA, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        title = ""

        setUpUI()
    }

    private fun setUpUI() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if(edit) {
            tv_welcome.text = "Edit Profile"
            tv_welcome_description.text = "The more your profile represents your personality, more people will be interested."
        } else {
            tv_welcome.text = String.format("Hi %s,", currentUser?.displayName)
        }

        tv_birth.text = birthDate.timeInMillis.toBirthDate()
        tv_birth.setOnClickListener { pickBirthDate() }

        val genderAdapter = SpinnerItemsAdapter(this, R.layout.profile_spinner_item, resources.getStringArray(R.array.genders), false)
        genderAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        val interestAdapter = SpinnerItemsAdapter(this, R.layout.profile_spinner_item, resources.getStringArray(R.array.interests), false)
        interestAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        sp_gender.adapter = genderAdapter
        sp_gender.onItemSelectedListener = this
        sp_interest.adapter = interestAdapter

        bt_ok.setOnClickListener { onBackPressed() }
        bt_cancel.setOnClickListener { onBackPressed() }
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
            adapter.setSelectedPosition(position)
        }
    }
}
