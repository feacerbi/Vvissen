package com.vvissen.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.vvissen.R
import com.vvissen.model.User
import org.parceler.Parcels

/**
 * A placeholder fragment containing a simple view.
 */
class UserPhotoFragment : Fragment() {

    companion object {
        val USER_EXTRA = "user"
        val PHOTO_EXTRA = "photoNumber"

        fun newInstance(user: User, photoNumber: Int): UserPhotoFragment {
            val fragment = UserPhotoFragment()
            val args = Bundle()
            args.putParcelable(USER_EXTRA, Parcels.wrap(user))
            args.putParcelable(PHOTO_EXTRA, Parcels.wrap(photoNumber))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_photo, container, false)

        val user = Parcels.unwrap(arguments?.getParcelable(USER_EXTRA)) as User
        val photoNumber = Parcels.unwrap(arguments?.getParcelable(PHOTO_EXTRA)) as Int

        val photo = user.photos[photoNumber]

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
