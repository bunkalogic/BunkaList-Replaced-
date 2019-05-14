package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ItemDetailsActivity
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import com.bunkalogic.bunkalist.Retrofit.Response.People.CastResult
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResultSeries
import org.jetbrains.anko.intentFor

class CastDataPersonAdapter(private val ctx: Context, private var mValues: List<CastResult>): RecyclerView.Adapter<CastDataPersonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast_people_data, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = mValues[position]

        val id = mItem.id
        val type = mItem.mediaType
        val title = mItem.title.toString()
        val name =  mItem.name.toString()

        holder.itemView.setOnClickListener {
            ctx.startActivity(ctx.intentFor<ItemDetailsActivity>(
                "id" to id,
                "type" to type,
                "title" to title,
                "name" to name
            ))
        }

        holder.bind(mItem)
    }

    inner class ViewHolder(mView: View): RecyclerView.ViewHolder(mView){
        private val textViewtitle: TextView = mView.findViewById(R.id.textViewTitleCastPeopleData)
        private val textViewcharacter: TextView = mView.findViewById(R.id.textViewCharacter)
        private val imageViewPoster: ImageView = mView.findViewById(R.id.imageViewCastPeopleData)
        private val textViewCountEpisode: TextView = mView.findViewById(R.id.textViewDataEpisodeCount)
        private val textViewCountEpisodeLabel: TextView = mView.findViewById(R.id.textViewLabelEpisodeCount)


        fun bind(mItem: CastResult){

        val type = mItem.mediaType.toString()

            if (type == "tv"){
                textViewtitle.text = mItem.name.toString()
            }else{
                textViewtitle.text = mItem.title.toString()
            }

        textViewcharacter.text = mItem.character.toString()

            val imagePoster = mItem.posterPath.toString()

            Glide.with(ctx)
                .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + imagePoster)
                .placeholder(R.color.colorPrimary)
                .centerCrop()
                .into(imageViewPoster)

            val id = mItem.id
            val title = mItem.title.toString()
            val name =  mItem.name.toString()

                imageViewPoster.setOnClickListener {
                ctx.startActivity(ctx.intentFor<ItemDetailsActivity>(
                    "id" to id,
                    "type" to type,
                    "title" to title,
                    "name" to name
                ))
            }

            val countEpisode = mItem.episodeCount.toString()

            if (countEpisode == "null"){
                textViewCountEpisode.visibility = View.GONE
                textViewCountEpisodeLabel.visibility = View.GONE
            }else{
                textViewCountEpisode.visibility = View.VISIBLE
                textViewCountEpisodeLabel.visibility = View.VISIBLE

                textViewCountEpisode.text = mItem.episodeCount.toString()
            }

        }

    }
}