package com.vvissen.model

import org.parceler.Parcel

@Parcel
class GroupRandom : GroupType() {

    init {
        name = "Random"
        size = 10
        info = "Go on a trip with other 9 people you`ll get to know"
    }

}