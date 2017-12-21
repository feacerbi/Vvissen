package com.vvissen.model

import org.parceler.Parcel
import java.util.*

@Parcel
data class House (
        var name: String = "",
        var price: Double = 0.0,
        var description: String = "",
        var address: String = "",
        var place: String = "",
        var rating: Float = 5F,
        var ratingCount: Int = 0,
        var favorite: Boolean = false,
        var photos: Array<String?> = arrayOf("error"),
        var packageTier: Package = VipPackage()) : Comparable<House> {

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

    override fun compareTo(other: House): Int {
        return this.name.compareTo(other.name)
    }

    fun createFakeHouse(): House {
        name = "The Great Tent House"
        price = 6800.0
        description = "Close to Sirena Club\n200m from the Beach\n4 Suites with Queen Size beds\n6 Bathrooms"
        address = "Rua Francisco Loup, 1109 - Praia de Maresias, São Sebastião - SP, 11600-000"
        place = "Cancún"
        photos = arrayOfNulls<String>(6)
        packageTier = PremiumPackage()
        rating = 4.5F
        ratingCount = 153
        favorite = true
        return this
    }

    fun createFakeHouse2(): House {
        name = "Hill Sight Infinite Pool"
        price = 4600.0
        description = "Close to Sirena Club\n200m from the Beach\n4 Suites with Queen Size beds\n6 Bathrooms"
        address = "Rua Francisco Loup, 1109 - Praia de Maresias, São Sebastião - SP, 11600-000"
        place = "Rio de Janeiro"
        photos = arrayOfNulls<String>(6)
        packageTier = LuxuryPackage()
        ratingCount = 58
        return this
    }

    fun createFakeHouse3(): House {
        name = "Cozy Non-Stop Party"
        price = 1900.0
        description = "Close to Sirena Club\n200m from the Beach\n4 Suites with Queen Size beds\n6 Bathrooms"
        address = "Rua Francisco Loup, 1109 - Praia de Maresias, São Sebastião - SP, 11600-000"
        place = "Maresias"
        photos = arrayOfNulls<String>(6)
        packageTier = VipPackage()
        rating = 4F
        ratingCount = 36
        favorite = true
        return this
    }
}