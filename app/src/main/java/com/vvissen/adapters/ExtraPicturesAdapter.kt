package com.vvissen.adapters

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.vvissen.R
import com.vvissen.activities.FullscreenActivity
import com.vvissen.adapters.listeners.ExtraPhotoClickListener
import com.vvissen.utils.inflate
import com.vvissen.utils.launchActivityWithExtras
import com.vvissen.utils.showListDialog
import kotlinx.android.synthetic.main.extra_photo.view.*

class ExtraPicturesAdapter(val listener: ExtraPhotoClickListener, private val profile: MutableList<Int> = mutableListOf())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val profilePhoto = profile[position]

        with(holder.itemView) {
            Picasso.with(context)
                    .load(profilePhoto)
                    .fit()
                    .centerCrop()
                    .into(iv_extra_photo)

            iv_extra_photo.setOnClickListener {
                listener.getActivity().launchActivityWithExtras<FullscreenActivity>(
                        FullscreenActivity::class,
                        arrayOf(FullscreenActivity.TITLE_EXTRA, FullscreenActivity.PHOTO_EXTRA),
                        arrayOf("", profilePhoto),
                        false)
            }

            iv_extra_photo.setOnLongClickListener {
                AlertDialog.Builder(context).showListDialog("Extra Photo", resources.getStringArray(R.array.extra_menu), {
                    _, which ->
                    if(which == 0) {
                        listener.onPhotoChosen(profilePhoto)
                    } else if (which == 1) {
                        val itemPosition = profile.indexOf(profilePhoto)
                        profile.removeAt(itemPosition)
                        notifyItemRemoved(itemPosition)
                    }
                })
                true
            }
        }

    }

    fun addPhoto(photo: Int) {
        profile.add(photo)
        notifyItemInserted(profile.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExtraPicturesHolder(parent.inflate(R.layout.extra_photo))
    }

    override fun getItemCount(): Int {
        return profile.size
    }

    open class ExtraPicturesHolder(view: View) : RecyclerView.ViewHolder(view)
}