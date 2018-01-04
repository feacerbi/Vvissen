package com.vvissen.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.vvissen.R
import com.vvissen.adapters.listeners.TripListClickListener
import com.vvissen.model.*
import com.vvissen.utils.inflate
import com.vvissen.utils.showOneChoiceCancelableDialog
import com.vvissen.utils.toCurrency
import com.vvissen.utils.toPeriodDate
import kotlinx.android.synthetic.main.trip_card_item.view.*
import java.util.*


class TripsAdapter(private val listener: TripListClickListener, private val trips: ArrayList<Trip> = arrayListOf())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val trip = trips[position]

        with(holder.itemView) {
            tv_place_name.text = trip.house.place
            tv_package_tier.text = trip.house.packageTier.name
            tv_house_title.text = trip.house.name
            tv_dates.text = String.format("${trip.period.first.toPeriodDate()} - ${trip.period.second.toPeriodDate()}")
            tv_house_people.text = String.format("${trip.groupType.name.toLowerCase()} trip")
            tv_house_price.text = trip.totalPrice().toCurrency()

            var tripStatus: Status = StatusPending()

            if (trip.isReady()) {
                tripStatus = StatusReady()
                tv_status.text = tripStatus.name.toLowerCase()
                tv_status.setTextColor(resources.getColor(tripStatus.color))
                iv_exit_button.visibility = View.INVISIBLE
            } else if(trip.isConfirmed()) {
                tv_status.text = String.format("${trip.readyGuests()}/${trip.groupType.size} ready")
                tv_status.setTextColor(resources.getColor(tripStatus.color))
            } else {
                tripStatus = StatusMatch()
                tv_status.text = String.format("${trip.confirmedGuests()}/${trip.groupType.size} matches")
                tv_status.setTextColor(resources.getColor(tripStatus.color))
            }

            val photo = if(trip.house.packageTier is PackagePremium) R.drawable.cancun else if(trip.house.packageTier is PackageLuxury) R.drawable.rio else R.drawable.maresias

            Picasso.with(context)
                    .load("error")
                    .fit()
                    .centerCrop()
                    .placeholder(photo)
                    .error(photo)
                    .into(iv_place_photo)

            iv_action_map.setOnClickListener {
                openMap(context, trip)
            }

            iv_action_share.setOnClickListener {
                share(context, trip, tripStatus)
            }

            iv_action_calendar.setOnClickListener {
                addToCalendar(context, trip)
            }

            iv_exit_button.setOnClickListener {
                AlertDialog.Builder(context).showOneChoiceCancelableDialog("Exit Trip", "Do you want to exit and remove this trip?", "Yes", {
                    _, _ ->
                    Toast.makeText(context, "Trip removed", Toast.LENGTH_SHORT).show()
                })
            }

            setOnClickListener { listener.onTripClicked(trip) }
        }

    }

    fun openMap(context: Context, trip: Trip) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo:0,0?q=" + trip.house.address)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(Intent.createChooser(intent, "Open in maps"))
        }
    }

    fun share(context: Context, trip: Trip, tripStatus: Status) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this trip to " + trip.house.place + " by Vvissen, it`s " + tripStatus.name + ".")
        sendIntent.type = "text/plain"
        context.startActivity(Intent.createChooser(sendIntent, "Send to"))
    }

    fun addToCalendar(context:Context, trip: Trip) {
        val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, "Trip to " + trip.house.place + " by Vvissen")
                .putExtra(CalendarContract.Events.DESCRIPTION, "House: " + trip.house.name + "\nType: " + trip.groupType.name)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, trip.house.address)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, trip.period.first)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, trip.period.second)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TripsViewHolder(parent.inflate(R.layout.trip_card_item))
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    open class TripsViewHolder(view: View) : RecyclerView.ViewHolder(view)
}