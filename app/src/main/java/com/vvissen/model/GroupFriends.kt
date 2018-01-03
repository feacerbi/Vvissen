package com.vvissen.model

import org.parceler.Parcel

@Parcel
class GroupFriends : GroupType(){

    init {
        name = "Friends"
        size = 10
        info = "Gather your friends and go have a dream like trip"
    }

}