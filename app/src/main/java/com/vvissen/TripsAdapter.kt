package com.vvissen

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.vvissen.model.LuxuryPackage
import com.vvissen.model.PremiumPackage
import com.vvissen.model.Trip
import kotlinx.android.synthetic.main.trip_card_item.view.*

class TripsAdapter(private val trips: ArrayList<Trip> = arrayListOf())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val trip = trips[position]

        with(holder.itemView) {
            tv_place_name.text = trip.house.place
            tv_package_tier.text = trip.house.packageTier.name
            tv_house_title.text = trip.house.name
            tv_dates.text = String.format("${trip.period.first.toPeriodDate()} - ${trip.period.second.toPeriodDate()}")
            tv_house_people.text = String.format("${trip.groupType.size} ${trip.groupType.name}")
            tv_house_price.text = String.format("U$ ${trip.house.price.format(2)}")

            val photo = if(trip.house.packageTier is PremiumPackage) R.drawable.cancun else if(trip.house.packageTier is LuxuryPackage) R.drawable.rio else R.drawable.maresias

            Picasso.with(context)
                    .load("error")
                    .fit()
                    .centerCrop()
                    .placeholder(photo)
                    .error(photo)
                    .into(iv_place_photo)
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