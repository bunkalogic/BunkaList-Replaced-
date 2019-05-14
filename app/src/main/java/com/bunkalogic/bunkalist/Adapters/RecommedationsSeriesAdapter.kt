package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ItemDetailsActivity
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResultSeries
import org.jetbrains.anko.intentFor

class RecommedationsSeriesAdapter(private val ctx: Context, private var mValues: List<ResultSeries>): RecyclerView.Adapter<RecommedationsSeriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommendations_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = mValues[position]

        val id = mItem.id
        val type = "tv"
        val title = mItem.name

        holder.itemView.setOnClickListener {
            ctx.startActivity(ctx.intentFor<ItemDetailsActivity>(
                "id" to id,
                "type" to type,
                "title" to title
            ))
        }

        holder.bind(mItem)


    }

    inner class ViewHolder(mView: View): RecyclerView.ViewHolder(mView){
        private val textViewTitle: TextView = mView.findViewById(R.id.textViewTitleRecommendation)
        private val imageViewPoster: ImageView = mView.findViewById(R.id.imageViewRecommendation)

        fun bind(mItem: ResultSeries){

            textViewTitle.text = mItem.name.toString()
            val poster = mItem.backdropPath.toString()

            Glide.with(ctx)
                .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_BACKDROP + poster)
                .placeholder(R.color.colorPrimary)
                .centerCrop()
                .into(imageViewPoster)

        }

    }
}