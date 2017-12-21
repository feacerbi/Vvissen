package com.vvissen.model

import org.parceler.Parcel

@Parcel
open class Package(
        var name: String = "VIP",
        var description: String = "Good package")