package com.vvissen.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.vvissen.R
import com.vvissen.model.Review
import com.vvissen.utils.inflate
import com.vvissen.utils.toMonthName
import com.vvissen.utils.toYear
import kotlinx.android.synthetic.main.review_list_item.view.*

class ReviewsAdapter(private var reviews: Array<Review> = arrayOf())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment = reviews[position]

        with(holder.itemView) {
            tv_reviewer_name.text = comment.reviewer.name
            tv_review_time_info.text = String.format("%s %s", comment.trip.period.first.toMonthName(), comment.trip.period.first.toYear())
            tv_review_type_info.text = comment.trip.groupType.name.toLowerCase()
            rb_rating_bar.rating = comment.review
            tv_rating.text = String.format("%.1f", comment.review)
            tv_comment.text = comment.comment

            Picasso.with(context)
                    .load("error")
                    .fit()
                    .centerCrop()
                    .placeholder(comment.reviewer.photos[0])
                    .error(comment.reviewer.photos[0])
                    .into(civ_reviewer_photo)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReviewsViewHolder(parent.inflate(R.layout.review_list_item))
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    fun setItems(items: Array<Review>) {
        reviews = items
        notifyDataSetChanged()
    }

    open class ReviewsViewHolder(view: View) : RecyclerView.ViewHolder(view)
}