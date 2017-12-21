package com.vvissen

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*

class DatePickerDialog : DialogFragment() {

    var dateListener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, dateListener, year, month, day)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DatePickerDialog.OnDateSetListener) {
            dateListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement DatePickerDialog.OnDateSetListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        dateListener = null
    }
}