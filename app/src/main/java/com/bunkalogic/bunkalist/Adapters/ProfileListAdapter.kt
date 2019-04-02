package com.bunkalogic.bunkalist.Adapters

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.OnGetMovieCallback
import com.bunkalogic.bunkalist.Retrofit.OnGetSeriesCallback
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.Movie
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.Series
import com.bunkalogic.bunkalist.data.ViewModelSearch
import com.bunkalogic.bunkalist.db.ItemListRating
import java.text.SimpleDateFormat

class ProfileListAdapter(private val ctx: Context, private var mValues: MutableList<ItemListRating>): RecyclerView.Adapter<ProfileListAdapter.ViewHolder>(){

    var searchViewModelSearch: ViewModelSearch? = null
    val MovieID = 1
    val SerieID = 2
    val AnimeID = 3

    init {
        searchViewModelSearch = ViewModelProviders.of(ctx as FragmentActivity).get(ViewModelSearch::class.java)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileListAdapter.ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.list_profile_fragement_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ProfileListAdapter.ViewHolder, position: Int) {
        val listRating = mValues[position]
        val numPosition = position + 1

        holder.numPosition.text = "$numPosition."
        holder.dateAt.text = SimpleDateFormat("dd.MM.yyyy").format(listRating.addDate)
        holder.yourRating.text = listRating.finalRate.toString()


        // TODO: Check the userId only show the current user

        val type = listRating.typeOeuvre
        val idItem = listRating.oeuvreId

        if (type == MovieID){
            searchViewModelSearch!!.getMovie(idItem!!,object : OnGetMovieCallback{
                override fun onSuccess(movie: Movie) {
                    holder.title.text = movie.title
                    holder.dateRelease.text = movie.releaseDate?.split("-")?.get(0) ?: movie.releaseDate
                    holder.description.text = movie.overview
                    holder.globalRating.text = movie.voteAverage.toString()

                    Glide.with(ctx)
                        .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + movie.posterPath)
                        .override(80, 120)
                        .into(holder.imagePoster)
                }

                override fun onError() {
                    Log.d("ProfileListAdapter", "Error Movie try Again")
                }

            })
        }else{
            searchViewModelSearch!!.getSeriesAndAnime(idItem!!, object : OnGetSeriesCallback{
                override fun onSuccess(series: Series) {
                    holder.title.text = series.name
                    holder.dateRelease.text = series.firstAirDate?.split("-")?.get(0) ?: series.firstAirDate
                    holder.description.text = series.overview
                    holder.globalRating.text = series.voteAverage.toString()

                    Glide.with(ctx)
                        .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + series.posterPath)
                        .override(80, 120)
                        .into(holder.imagePoster)
                }

                override fun onError() {
                    Log.d("ProfileListAdapter", "Error Movie try Again")
                }

            })
        }

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