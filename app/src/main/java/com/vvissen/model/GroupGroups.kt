package com.vvissen.model

import org.parceler.Parcel

@Parcel
class GroupGroups : GroupType() {

    init {
        name = "Groups"
        size = 10
        info = "Group up and go travel with another group of interest"
    }

}