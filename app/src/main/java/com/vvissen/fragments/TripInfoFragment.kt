package com.vvissen.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vvissen.R
import com.vvissen.model.*
import com.vvissen.utils.showTextDialog
import com.vvissen.utils.toCurrency
import com.vvissen.utils.toFullPeriodDate
import kotlinx.android.synthetic.main.fragment_trip_info.view.*
import org.parceler.Parcels

class TripInfoFragment : Fragment() {

    private var trip: Trip? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            trip = Parcels.unwrap(arguments?.getParcelable(TRIP_EXTRA))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_trip_info, container, false)

        var tripStatus: Status = StatusPending()

        if(view is NestedScrollView) {
            with(view) {
                val currentTrip = trip

                if (currentTrip != null) {
                    if (currentTrip.isReady()) {
                        tripStatus = StatusReady()
                        tv_status_text.text = tripStatus.name
                        tv_status_text.setTextColor(resources.getColor(tripStatus.color))

                    } else if(currentTrip.isConfirmed()) {
                        tv_status_text.text = String.format("${currentTrip.readyGuests()}/${currentTrip.groupType.size} ready")
                        tv_status_text.setTextColor(resources.getColor(tripStatus.color))
                    } else {
                        tripStatus = StatusMatch()
                        tv_status_text.text = String.format("${currentTrip.confirmedGuests()}/${currentTrip.groupType.size} matches")
                        tv_status_text.setTextColor(resources.getColor(tripStatus.color))
                    }

                    tv_calendar_from.text = currentTrip.period.first.toFullPeriodDate()
                    tv_calendar_to.text = currentTrip.period.second.toFullPeriodDate()
                    iv_calendar_open_icon.setOnClickListener {
                        addToCalendar(currentTrip)
                    }

                    tv_type_text.text = currentTrip.groupType.name
                    iv_type_info.setOnClickListener {
                        showInfoDialog("Type Info", currentTrip.groupType.info)
                    }

                    tv_price_text_total.text = currentTrip.totalPrice().toCurrency()
                }
            }
        }

        return view
    }

    fun showInfoDialog(title: String, info: String?) {
        val here = context

        if(here != null && info != null) {
            AlertDialog.Builder(here).showTextDialog(title, info + ".")
        }
    }

    fun addToCalendar(currentTrip: Trip) {
        val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, "Trip to " + currentTrip.house.place + " by Vvissen")
                .putExtra(CalendarContract.Events.DESCRIPTION, "House: " + currentTrip.house.name + "\nType: " + currentTrip.groupType.name)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, currentTrip.house.address)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, currentTrip.period.first)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, currentTrip.period.second)
        if (intent.resolveActivity(context?.packageManager) != null) {
            context?.startActivity(intent)
        }
    }

    companion object {
        val TRIP_EXTRA = "trip"

        fun newInstance(trip: Trip): TripInfoFragment {
            val fragment = TripInfoFragment()
            val args = Bundle()
            args.putParcelable(TRIP_EXTRA, Parcels.wrap(trip))
            fragment.arguments = args
            return fragment
        }
    }

}
