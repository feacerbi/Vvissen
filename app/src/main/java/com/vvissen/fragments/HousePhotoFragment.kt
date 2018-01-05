package com.vvissen.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.vvissen.R
import com.vvissen.activities.FullscreenActivity
import com.vvissen.model.House
import com.vvissen.model.PackageLuxury
import com.vvissen.model.PackagePremium
import com.vvissen.model.PackageVip
import com.vvissen.utils.launchActivityWithExtras
import org.parceler.Parcels

/**
 * A placeholder fragment containing a simple view.
 */
class HousePhotoFragment : Fragment() {

    companion object {
        val HOUSE_EXTRA = "house"
        val PHOTO_EXTRA = "photoNumber"

        fun newInstance(house: House, photoNumber: Int): HousePhotoFragment {
            val fragment = HousePhotoFragment()
            val args = Bundle()
            args.putParcelable(HOUSE_EXTRA, Parcels.wrap(house))
            args.putParcelable(PHOTO_EXTRA, Parcels.wrap(photoNumber))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_photo, container, false)

        val house = Parcels.unwrap(arguments?.getParcelable(HOUSE_EXTRA)) as House
        val photoNumber = Parcels.unwrap(arguments?.getParcelable(PHOTO_EXTRA)) as Int

        var photo = R.drawable.cancun_front

        when(house.packageTier.name) {
            PackagePremium().name -> {
                photo = when(photoNumber) {
                    0 -> R.drawable.cancun_front
                    1 -> R.drawable.cancun_bedroom
                    2 -> R.drawable.cancun_bathroom
                    3 -> R.drawable.cancun_outside
                    4 -> R.drawable.cancun_pool
                    5 -> R.drawable.cancun_stairs
                    else -> R.drawable.cancun_front
                }
            }
            PackageLuxury().name -> {
                photo = when (photoNumber) {
                    0 -> R.drawable.rio_bar
                    1 -> R.drawable.rio_bath
                    2 -> R.drawable.rio_eat
                    3 -> R.drawable.rio_living
                    4 -> R.drawable.rio_room
                    5 -> R.drawable.rio_tv
                    6 -> R.drawable.rio_out
                    else -> R.drawable.rio_out
                }
            }
            PackageVip().name -> {
                photo = when (photoNumber) {
                    0 -> R.drawable.mare_barb
                    1 -> R.drawable.mare_cine
                    2 -> R.drawable.mare_living
                    3 -> R.drawable.mare_out
                    4 -> R.drawable.mare_room
                    5 -> R.drawable.mare_solar
                    6 -> R.drawable.mare_tub
                    else -> R.drawable.cancun_front
                }
            }
        }

        if(view is ImageView) {
            with(view) {
                Picasso.with(context)
                        .load(photo)
                        .fit()
                        .centerCrop()
                        .into(view)
            }
        }

        view.setOnClickListener {
            launchActivityWithExtras<FullscreenActivity>(
                    FullscreenActivity::class,
                    arrayOf(FullscreenActivity.TITLE_EXTRA, FullscreenActivity.PHOTO_EXTRA),
                    arrayOf(house.name, photo),
                    false)
        }

        return view
    }
}
