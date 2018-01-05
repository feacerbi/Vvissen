package com.vvissen.model

import com.vvissen.R
import org.parceler.Parcel
import java.util.*

@Parcel
data class User(
        var name: String = "",
        var age: Int = 18,
        var photos: Array<Int> = arrayOf(),
        var shortDescription: String = "",
        var gender: Gender = Gender.Man,
        var interest: Interest = Interest.Women) {

    fun createFakeUser(): User {
        name = "Anna"
        age = 24
        photos = arrayOf(R.drawable.brunette, R.drawable.brunette2)
        gender = Gender.Woman
        interest = Interest.Men
        shortDescription = "I`m really into photography."
        return this
    }

    fun createFakeUser2(): User {
        name = "Vanessa"
        age = 27
        photos = arrayOf(R.drawable.brunette4, R.drawable.brunette5, R.drawable.brunette6)
        gender = Gender.Woman
        interest = Interest.Men
        shortDescription = "Pretty much a music lover."
        return this
    }

    fun createFakeUser3(): User {
        name = "Marcella Oliveira"
        age = 27
        photos = arrayOf(R.drawable.blonde, R.drawable.blonde2)
        gender = Gender.Woman
        interest = Interest.Men
        shortDescription = "Beaches, sand, Sun and sea are my thing."
        return this
    }

    fun createFakeUser4(): User {
        name = "Clara"
        age = 32
        photos = arrayOf(R.drawable.red)
        gender = Gender.Woman
        interest = Interest.Men
        shortDescription = "I`m always up for a night out till the sunrise."
        return this
    }

    fun createFakeUser5(): User {
        name = "Kayla"
        age = 22
        photos = arrayOf(R.drawable.japa)
        gender = Gender.Woman
        interest = Interest.Men
        shortDescription = "A good time spent in 2 is my first choice."
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (name != other.name) return false
        if (age != other.age) return false
        if (!Arrays.equals(photos, other.photos)) return false
        if (shortDescription != other.shortDescription) return false
        if (gender != other.gender) return false
        if (interest != other.interest) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        result = 31 * result + Arrays.hashCode(photos)
        result = 31 * result + shortDescription.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + interest.hashCode()
        return result
    }

}