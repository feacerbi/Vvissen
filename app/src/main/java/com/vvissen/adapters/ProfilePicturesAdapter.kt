package com.vvissen.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.vvissen.R
import com.vvissen.inflate
import kotlinx.android.synthetic.main.profile_picture_item.view.*

class ProfilePicturesAdapter(private val profile: Array<Int> = arrayOf())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val profilePhoto = profile[position]

        with(holder.itemView) {
            Picasso.with(context)
                    .load(profilePhoto)
                    .fit()
                    .centerCrop()
                    .into(iv_profile_picture)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProfilePicturesHolder(parent.inflate(R.layout.profile_picture_item))
    }

    override fun getItemCount(): Int {
        return profile.size
    }

    open class ProfilePicturesHolder(view: View) : RecyclerView.ViewHolder(view)
}