package com.vvissen.adapters.listeners

import com.vvissen.model.Trip

interface TripListClickListener {
    fun onTripClicked(trip: Trip)
}