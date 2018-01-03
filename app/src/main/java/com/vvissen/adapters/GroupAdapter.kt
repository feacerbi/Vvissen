package com.vvissen.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.vvissen.R
import com.vvissen.model.Trip
import com.vvissen.utils.inflate
import kotlinx.android.synthetic.main.user_confirmed_item.view.*

class GroupAdapter(private var trip: Trip? = null)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentTrip = trip

        if(currentTrip != null) {
            val user = currentTrip.guests.keys.elementAt(position)
            val status = currentTrip.guests.values.elementAt(position)

            with(holder.itemView) {
                tv_name.text = user.name

                Picasso.with(context)
                        .load(user.photos[0])
                        .resize(300, 300)
                        .centerCrop()
                        .into(iv_profile_picture)

                tv_status.text = status.name
                tv_status.setTextColor(resources.getColor(status.color))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GroupViewHolder(parent.inflate(R.layout.user_confirmed_item))
    }

    override fun getItemCount(): Int {
        return trip?.guests?.size ?: 0
    }

    fun setItem(item: Trip) {
        trip = item
        notifyDataSetChanged()
    }

    open class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view)
}