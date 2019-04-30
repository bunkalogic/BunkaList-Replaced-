package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ItemDetailsActivity
import com.bunkalogic.bunkalist.Dialog.AddListDialog
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import org.jetbrains.anko.intentFor

class TopListMoviesAdapter (private val ctx: Context, private var mValues: ArrayList<ResultMovie>): RecyclerView.Adapter<TopListMoviesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.top_list_item_movie, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val numPosition = position + 1
        holder.textViewTopPosition.text = "$numPosition."
            holder.bind(mValues[position])
    }

    fun appendMovies(moviesToAppend: List<ResultMovie>) {
        mValues.addAll(moviesToAppend)
        notifyDataSetChanged()
    }

    fun clearList(){
        mValues.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(mView: View): RecyclerView.ViewHolder(mView){
        private val imageViewPoster: ImageView = mView.findViewById(R.id.imageViewPosterTopMovies)
        private val textViewTitle: TextView = mView.findViewById(R.id.textViewTitleTopMovies)
        private val textViewDateReleast :TextView = mView.findViewById(R.id.textViewDateReleastTopMovies)
        private val textViewDescription: TextView = mView.findViewById(R.id.textViewDescriptionTopMovies)
        private val textViewSeeDetails: TextView = mView.findViewById(R.id.textViewGetFullDetailsTopMovies)
        private val imageViewRating: ImageView = mView.findViewById(R.id.imageViewAddToMyListTopMovies)
        private val textViewRating: TextView = mView.findViewById(R.id.textViewRatingTopMovies)
        internal var textViewTopPosition: TextView = mView.findViewById(R.id.textViewTopListMovies)

        fun bind(mItem: ResultMovie){

            textViewTitle.text = mItem.title

            textViewDescription.text = mItem.overview

            textViewDateReleast.text = mItem.releaseDate?.split("-")?.get(0) ?: mItem.releaseDate

            textViewRating.text = mItem.voteAverage.toString()

            val photo = mItem.posterPath

            Glide.with(ctx)
                .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + photo)
                .into(imageViewPoster)

            val id = mItem.id
            val type = "movie"
            val title = mItem.title


            textViewSeeDetails.setOnClickListener {
                ctx.startActivity(ctx.intentFor<ItemDetailsActivity>(
                    "id" to id,
                    "type" to type,
                    "title" to title
                ))
            }

            imageViewRating.setOnClickListener {
                preferences.itemID = id!!
                mItem.title = preferences.itemName
                val manager = (ctx as AppCompatActivity).supportFragmentManager
                AddListDialog().show(manager, "")
            }

        }
    }

}