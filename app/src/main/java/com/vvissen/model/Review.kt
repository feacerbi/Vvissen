package com.vvissen.model

import org.parceler.Parcel

@Parcel
data class Review(
        var reviewer: User = User(),
        var trip: Trip = Trip(),
        var review: Float = 5.0F,
        var comment: String = "") {

    fun createFakeReview(): Review {
        reviewer = User().createFakeUser()
        trip = Trip().createFakeTrip()
        review = 4.1F
        comment = "This place is awesome. It\\'s ideally located close to Lima with its long shopping street, a few metro stops away from the duomo (very convenient for visiting the historical heart of Milan) and just an 8 mins walk away from the central station, which was extremely useful to us since we had our flights from the Malpensa airport. The apartment comes with some great amenities (tea, coffee, cookies + fully equipped kitchen) and lovely touches. Would definitely recommend to anyone!"
        return this
    }

    fun createFakeReview2(): Review {
        reviewer = User().createFakeUser2()
        trip = Trip().createFakeTrip2()
        review = 5.0F
        comment = "Amazing place!! So close to the Lima Station that will take you to entrance of the Duomo, Santa Maria Della Grazie (Last Supper) and about 8 min walk to the Milano Centrale Station. The place was so comfortable and clean. We enjoyed our stay very much. Nir and Tom were very helpful and welcoming. I felt very safe and well guided. Definitely recommend! Best Airbnb we have stayed at in Europe thus far. Thank you!"
        return this
    }

    fun createFakeReview3(): Review {
        reviewer = User().createFakeUser3()
        trip = Trip().createFakeTrip3()
        review = 4.5F
        comment = "Thoroughly recommend this fabulous apartment. It was our first air bnb (URL HIDDEN) we were spoilt…Noa was lovely and very helpful. All communication was fantastic from first booking to the final time we left. Thank you!"
        return this
    }
}