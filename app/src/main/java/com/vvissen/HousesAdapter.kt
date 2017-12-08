package com.vvissen

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.vvissen.model.House
import com.vvissen.model.LuxuryPackage
import com.vvissen.model.PremiumPackage
import kotlinx.android.synthetic.main.house_card_item.view.*
import java.text.NumberFormat
import java.util.*

class HousesAdapter(private val houses: ArrayList<House> = arrayListOf())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val house = houses[position]

        with(holder.itemView) {
            tv_place_name.text = house.place
            tv_package_tier.text = house.packageTier.name
            tv_house_title.text = house.name
            tv_house_price.text = String.format("U${NumberFormat.getCurrencyInstance(Locale.getDefault()).format(house.price).noDecimals()} per weekend")
            tv_house_rating_grade.text = house.rating.toString()
            tv_house_rating.rating = house.rating

            val photo = if(house.packageTier is PremiumPackage) R.drawable.cancun else if(house.packageTier is LuxuryPackage) R.drawable.rio else R.drawable.maresias

            Picasso.with(context)
                    .load("error")
                    .fit()
                    .centerCrop()
                    .placeholder(photo)
                    .error(photo)
                    .into(iv_place_photo)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HousesViewHolder(parent.inflate(R.layout.house_card_item))
    }

    override fun getItemCount(): Int {
        return houses.size
    }

    open class HousesViewHolder(view: View) : RecyclerView.ViewHolder(view)
}