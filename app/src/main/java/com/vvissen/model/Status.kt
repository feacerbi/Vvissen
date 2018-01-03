package com.vvissen.model

import com.vvissen.R
import org.parceler.Parcel

@Parcel
open class Status(
        var name: String = "Pending",
        var color: Int = R.color.colorAccent)