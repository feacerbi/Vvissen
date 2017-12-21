package com.vvissen.model

import com.vvissen.rollDays
import java.util.*

data class Trip(
        var house: House = House(),
        var guests: MutableMap<User, Boolean> = mutableMapOf(User("1") to true, User("2") to true),
        var groupType: GroupType = GroupRandom(),
        var confirmed: Boolean = false,
        var period: Pair<Long, Long> = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().timeInMillis)
) {

    fun createFakeTrip(): Trip {
        house = House().createFakeHouse()
        groupType = GroupRandom()
        period = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().rollDays(10).timeInMillis)
        confirmedGuests()
        return this
    }

    fun createFakeTrip2(): Trip {
        house = House().createFakeHouse2()
        guests = mutableMapOf(Pair(User("1"), false), Pair(User("2"), true), Pair(User("3"), true), Pair(User("4"), false), Pair(User("5"), true), Pair(User("6"), true), Pair(User("7"), false), Pair(User("8"), true), Pair(User("9"), true), Pair(User("10"), true))
        groupType = GroupGroups()
        period = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().rollDays(30).timeInMillis)
        confirmed = true
        return this
    }

    fun createFakeTrip3(): Trip {
        house = House().createFakeHouse3()
        guests = mutableMapOf(Pair(User("1"), true), Pair(User("2"), true), Pair(User("3"), true), Pair(User("4"), true), Pair(User("5"), true), Pair(User("6"), true), Pair(User("7"), true), Pair(User("8"), true), Pair(User("9"), true), Pair(User("10"), true))
        groupType = GroupFriends()
        period = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().rollDays(18).timeInMillis)
        confirmed = true
        return this
    }

    fun confirmedGuests(): Int {
        var confirmedNumber = 0

        for((_, confirmed) in guests) {
            if(confirmed) confirmedNumber++
        }

        return confirmedNumber
    }
}