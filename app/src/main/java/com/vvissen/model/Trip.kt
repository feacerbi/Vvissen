package com.vvissen.model

import com.vvissen.rollDays
import java.util.*

data class Trip(
        var house: House = House(),
        var guests: Array<User> = arrayOf(User()),
        var groupType: GroupType = GroupUnknown(),
        var period: Pair<Long, Long> = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().timeInMillis)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Trip

        if (house != other.house) return false
        if (!Arrays.equals(guests, other.guests)) return false
        if (period != other.period) return false

        return true
    }

    override fun hashCode(): Int {
        var result = house.hashCode()
        result = 31 * result + Arrays.hashCode(guests)
        result = 31 * result + period.hashCode()
        return result
    }

    fun createFakeTrip(): Trip {
        house = House().createFakeHouse()
        guests = arrayOf(User())
        groupType = GroupUnknown()
        period = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().rollDays(10).timeInMillis)
        return this
    }

    fun createFakeTrip2(): Trip {
        house = House().createFakeHouse2()
        guests = arrayOf(User())
        groupType = GroupFriends55()
        period = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().rollDays(30).timeInMillis)
        return this
    }

    fun createFakeTrip3(): Trip {
        house = House().createFakeHouse3()
        guests = arrayOf(User())
        groupType = GroupFriends10()
        period = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().rollDays(18).timeInMillis)
        return this
    }
}