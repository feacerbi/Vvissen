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
import kotlinx.android.synthetic.main.fragment_photo_group.view.*
import org.parceler.Parcels

/**
 * A placeholder fragment containing a simple view.
 */
class GroupPhotoFragment : Fragment() {

    companion object {
        val GROUP_EXTRA = "group"

        fun newInstance(group: ArrayList<User>): GroupPhotoFragment {
            val fragment = GroupPhotoFragment()
            val args = Bundle()
            args.putParcelable(GROUP_EXTRA, Parcels.wrap(group))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_photo_group, container, false)

        val group = Parcels.unwrap(arguments?.getParcelable(GROUP_EXTRA)) as List<User>

        val photo1 = group[0].photos[0]
        val photo2 = group[1].photos[0]
        val photo3 = group[2].photos[0]
        val photo4 = group[3].photos[0]
        val photo5 = group[4].photos[0]

        if(view is ImageView) {
            with(view) {
                Picasso.with(context)
                        .load(photo1)
                        .fit()
                        .centerCrop()
                        .into(iv_photo1)
                Picasso.with(context)
                        .load(photo2)
                        .fit()
                        .centerCrop()
                        .into(iv_photo2)
                Picasso.with(context)
                        .load(photo3)
                        .fit()
                        .centerCrop()
                        .into(iv_photo3)
                Picasso.with(context)
                        .load(photo4)
                        .fit()
                        .centerCrop()
                        .into(iv_photo4)
                Picasso.with(context)
                        .load(photo5)
                        .fit()
                        .centerCrop()
                        .into(iv_photo5)
            }
        }

        return view
    }
}
