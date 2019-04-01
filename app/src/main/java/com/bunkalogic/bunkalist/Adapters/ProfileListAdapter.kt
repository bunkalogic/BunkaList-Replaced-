package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.db.ItemListRating

class ProfileListAdapter(private val ctx: Context, private var mValues: MutableList<ItemListRating>): RecyclerView.Adapter<ProfileListAdapter.ViewHolder>(){


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProfileListAdapter.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: ProfileListAdapter.ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    inner class ViewHolder internal constructor(view: View): RecyclerView.ViewHolder(view){
        internal var title: TextView
        internal var dateRelease: TextView
        internal var description: TextView
        internal var globalRating: TextView
        internal var getFullDetails: TextView
        internal var numPosition: TextView
        internal var dateAt: TextView
        internal var yourRating: TextView
        internal var imagePoster: ImageView

        init {
            title = view.findViewById(R.id.textViewTitleListProfile)
            dateRelease = view.findViewById(R.id.textViewDateReleastListProfile)
            description = view.findViewById(R.id.textViewDescriptionListProfile)
            globalRating = view.findViewById(R.id.textViewRatingListProfile)
            getFullDetails = view.findViewById(R.id.textViewGetFullDetailsListProfile)
            numPosition = view.findViewById(R.id.textViewListProfileNumberPosition)
            dateAt = view.findViewById(R.id.textViewListProfileDateAt)
            yourRating = view.findViewById(R.id.textViewListProfileYourRating)
            imagePoster = view.findViewById(R.id.imageViewPosterListProfile)
        }
    }

}