package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.db.NewReview
import java.text.SimpleDateFormat

class ReviewAdapter(val ctx: Context, private val reviewList : MutableList<NewReview>) : androidx.recyclerview.widget.RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_review_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        val reviewItem = reviewList[position]

        holder.titleReview.text = reviewItem.titleReview
        holder.usernameReview.text = reviewItem.username
        holder.dateOnReview.text = SimpleDateFormat("h:mm EEE MMM YYY").format(reviewItem.sentAt)

        if (reviewItem.isSpoiler == true){

            holder.contentReview.text = ctx.getString(R.string.fragment_review_content_spoiler)
        }else{
            holder.contentReview.text = reviewItem.contentReview
        }

        holder.contentReview.setOnClickListener {
            holder.contentReview.text = reviewItem.contentReview
        }

        if (reviewItem.profileImageUrl!!.isEmpty()){
            Glide.with(holder.itemView)
                .load(R.drawable.ic_person_black_24dp)
                .apply(RequestOptions.circleCropTransform().override(80, 80))
                .into(holder.imageUser)
        }else{
            Glide.with(holder.itemView)
                .load(reviewItem.profileImageUrl)
                .apply(RequestOptions.circleCropTransform().override(80, 80))
                .into(holder.imageUser)
        }


    }


    inner class ViewHolder internal constructor(view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view){
        internal var titleReview : TextView
        internal var usernameReview : TextView
        internal var dateOnReview : TextView
        internal var contentReview : TextView
        internal var imageUser : ImageView

        init {
            titleReview = view.findViewById(R.id.textViewTitleReview)
            usernameReview = view.findViewById(R.id.textViewUsernameReview)
            dateOnReview = view.findViewById(R.id.textViewDateReview)
            contentReview = view.findViewById(R.id.textViewContentReview)
            imageUser = view.findViewById(R.id.imageViewUserImageReview)
        }


    }

}