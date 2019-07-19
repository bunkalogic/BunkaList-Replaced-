package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ItemDetailsActivity
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ListTopsActivity
import com.bunkalogic.bunkalist.Models.TopsCardItem
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import org.jetbrains.anko.intentFor

class TopsOuevreAdapter(private val ctx: Context,val mValues: List<TopsCardItem>): androidx.recyclerview.widget.RecyclerView.Adapter<TopsOuevreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_item_tops, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = mValues[position]

        val id = mItem.id
        val title = mItem.title



        holder.itemView.setOnClickListener {
            ctx.startActivity(ctx.intentFor<ListTopsActivity>(
                "idTops" to id,
                "title" to title
            ))
        }

        holder.bind(mItem)


    }

    inner class ViewHolder(mView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(mView){
        private val textViewTitle: TextView = mView.findViewById(R.id.textViewTitleTops)
        private val imageViewPoster1: ImageView = mView.findViewById(R.id.imageViewPosterTops1)
        private val imageViewPoster2: ImageView = mView.findViewById(R.id.imageViewPosterTops2)
        private val imageViewPoster3: ImageView = mView.findViewById(R.id.imageViewPosterTops3)
        private val imageViewPoster4: ImageView = mView.findViewById(R.id.imageViewPosterTops4)

        fun bind(mItem: TopsCardItem){

            textViewTitle.text = mItem.title


            Glide.with(ctx)
                .load(mItem.image1)
                .placeholder(R.drawable.ic_placeholder_image)
                .centerCrop()
                .into(imageViewPoster1)

            Glide.with(ctx)
                .load(mItem.image2)
                .placeholder(R.drawable.ic_placeholder_image)
                .centerCrop()
                .into(imageViewPoster2)

            Glide.with(ctx)
                .load(mItem.image3)
                .placeholder(R.drawable.ic_placeholder_image)
                .centerCrop()
                .into(imageViewPoster3)

            Glide.with(ctx)
                .load(mItem.image4)
                .placeholder(R.drawable.ic_placeholder_image)
                .centerCrop()
                .into(imageViewPoster4)

        }

    }
}