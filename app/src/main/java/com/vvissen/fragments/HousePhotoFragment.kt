package com.vvissen.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.vvissen.R
import com.vvissen.model.House
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

        val photo = when(photoNumber) {
            0 -> R.drawable.front
            1 -> R.drawable.bedroom
            2 -> R.drawable.bathroom
            3 -> R.drawable.outside
            4 -> R.drawable.pool
            5 -> R.drawable.stairs
            else -> R.drawable.front
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

        return view
    }
}
