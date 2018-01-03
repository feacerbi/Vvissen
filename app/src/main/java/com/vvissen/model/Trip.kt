package com.vvissen.model

import com.vvissen.utils.daysTo
import com.vvissen.utils.rollDays
import org.parceler.Parcel
import java.util.*

@Parcel
data class Trip(
        var house: House = House(),
        var guests: MutableMap<User, Status> = mutableMapOf(),
        var groupType: GroupType = GroupRandom(),
        var period: Pair<Long, Long> = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().timeInMillis)
) {

    fun createFakeTrip(): Trip {
        house = House().createFakeHouse()
        groupType = GroupFriends()
        period = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().rollDays(10).timeInMillis)
        getUserList1().forEach { guests.put(it, StatusPending()) }
        getUserList2().forEach { guests.put(it, StatusReady()) }
        return this
    }

    fun createFakeTrip2(): Trip {
        house = House().createFakeHouse2()
        groupType = GroupGroups()
        period = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().rollDays(30).timeInMillis)
        getUserList1().forEach { guests.put(it, StatusReady()) }
        getUserList2().forEach { guests.put(it, StatusReady()) }
        return this
    }

    fun createFakeTrip3(): Trip {
        house = House().createFakeHouse3()
        groupType = GroupRandom()
        period = Pair(Calendar.getInstance().timeInMillis, Calendar.getInstance().rollDays(18).timeInMillis)
        getUserList1().forEach { guests.put(it, StatusLiked()) }
        getUserList2().forEach { guests.put(it, StatusMatch()) }
        return this
    }

    fun getUserList1() = arrayListOf(
            User().createFakeUser(),
            User().createFakeUser2(),
            User().createFakeUser3())

    fun getUserList2() = arrayListOf(
            User().createFakeUser4(),
            User().createFakeUser5())

    fun isConfirmed() = confirmedGuests() == groupType.size/2
    fun isReady() = readyGuests() == groupType.size/2

    fun confirmedGuests(): Int {
        var confirmedNumber = 0

        for((_, status) in guests) {
            if(status.name == StatusMatch().name) confirmedNumber++
            if(status.name == StatusPending().name) confirmedNumber++
            if(status.name == StatusReady().name) confirmedNumber++
        }

        return confirmedNumber
    }

    fun readyGuests(): Int {
        var readyNumber = 0

        for((_, status) in guests) {
            if(status.name == StatusReady().name) readyNumber++
        }

        return readyNumber
    }

    fun totalDays() = period.first.daysTo(period.second)

    fun totalPrice(): Double = house.price / 2 * totalDays()
}