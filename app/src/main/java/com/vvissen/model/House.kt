package com.vvissen.model

import java.util.*

data class House(
        var name: String = "",
        var price: Double = 0.0,
        var description: String = "",
        var address: String = "",
        var place: String = "",
        var rating: Float = 5F,
        var photos: Array<String> = arrayOf("error"),
        var packageTier: Package = PremiumPackage()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as House

        if (name != other.name) return false
        if (price != other.price) return false
        if (description != other.description) return false
        if (address != other.address) return false
        if (!Arrays.equals(photos, other.photos)) return false
        if (packageTier != other.packageTier) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + Arrays.hashCode(photos)
        result = 31 * result + packageTier.hashCode()
        return result
    }

    fun createFakeHouse(): House {
        name = "The Great Tent House"
        price = 6800.0
        description = "Nice house."
        address = "Some street in Cancún"
        place = "Cancún"
        photos = arrayOf("error")
        packageTier = PremiumPackage()
        rating = 4.5F
        return this
    }

    fun createFakeHouse2(): House {
        name = "Hill Sight Infinite Pool"
        price = 4600.0
        description = "Nice house."
        address = "Some street in Rio"
        place = "Rio de Janeiro"
        photos = arrayOf("error")
        packageTier = LuxuryPackage()
        return this
    }

    fun createFakeHouse3(): House {
        name = "Cozy Non-Stop Party"
        price = 1900.0
        description = "Nice house."
        address = "Some street in Maresias"
        place = "Maresias"
        photos = arrayOf("error")
        packageTier = VipPackage()
        rating = 4F
        return this
    }
}