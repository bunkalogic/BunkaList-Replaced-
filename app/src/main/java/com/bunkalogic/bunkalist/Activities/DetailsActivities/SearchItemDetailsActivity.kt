package com.bunkalogic.bunkalist.Activities.DetailsActivities

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.OnGetMovieCallback
import com.bunkalogic.bunkalist.Retrofit.OnGetSeriesCallback
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.Movie
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.Series
import com.bunkalogic.bunkalist.data.ViewModelSearch
import com.bunkalogic.bunkalist.db.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.search_item_full_details.*
import org.jetbrains.anko.toast


class SearchItemDetailsActivity : AppCompatActivity() {

    private lateinit var searchViewModel: ViewModelSearch

    private lateinit var toolbar: android.support.v7.widget.Toolbar

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.search_item_full_details)
        super.onCreate(savedInstanceState)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //instantiate the ViewModel
        searchViewModel = ViewModelProviders.of(this).get(ViewModelSearch::class.java)
        setUpCurrentUser()
        isMovieOrSerie()
    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    private fun isMovieOrSerie(){
        val type = "tv"
        val extrasType = intent.extras.getString("type")
        Log.d("SearchItemDetailsAct", "type: $extrasType")

        if (extrasType == type){
            isSerieAndAnime()
        }else{
            isMovie()
        }
    }

    private fun isMovie(){
        getMovieContentForID(object: OnGetMovieCallback{
            override fun onSuccess(movie: Movie) {
                textViewTitleDetails.text = movie.title
                textViewDateRelease.text = movie.releaseDate
                textViewTitleOriginal.text = movie.originalTitle
                textViewsearchItemdetailsNameDirector.text = "No data"
                textViewSearchItemDetailsRate.text = movie.voteAverage.toString()
                textViewSearchItemDetailsStatus.text = movie.status
                textViewSearchDetailsAllDescription.text = movie.overview

                val imageBackground = movie.backdropPath
                val imagePoster = movie.posterPath
                // Here I check if imageBackground is different from null and if it is null charge the imagePoster
                if (imageBackground != null){
                    Glide.with(this@SearchItemDetailsActivity)
                        .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_BACKDROP + imageBackground)
                        .into(imageViewBackDrop)
                }else{
                    Glide.with(this@SearchItemDetailsActivity)
                        .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + imagePoster)
                        .into(imageViewItemSearchDetailsPoster)
                }

                // Here I do the same process but in reverse
                if (imagePoster != null){
                    Glide.with(this@SearchItemDetailsActivity)
                        .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + imagePoster)
                        .into(imageViewItemSearchDetailsPoster)
                }else{
                    Glide.with(this@SearchItemDetailsActivity)
                        .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_BACKDROP + imageBackground)
                        .into(imageViewBackDrop)
                }
            }

            override fun onError() {
                Log.d("SearchItemDetailsActMov", "Error data Movie")
                toast("Please check your internet connection")
            }

        })
    }

    private fun getMovieContentForID(callback: OnGetMovieCallback){
        val extrasIdMovies = intent.extras.getInt("id")
        Log.d("SearchItemDetailsActMov", "Id: $extrasIdMovies")
        searchViewModel.getMovie(extrasIdMovies, callback)
    }

    private fun isSerieAndAnime(){
            getSeriesContentForID(object : OnGetSeriesCallback{
                override fun onSuccess(series: Series) {
                    textViewTitleDetails.text = series.name
                    textViewDateRelease.text = series.firstAirDate
                    textViewTitleOriginal.text = series.originalName
                    textViewsearchItemdetailsNameDirector.text = "No data"
                    textViewSearchItemDetailsRate.text = series.voteAverage.toString()
                    textViewSearchItemDetailsStatus.text = series.status
                    textViewSearchDetailsAllDescription.text = series.overview

                    val imageBackground = series.backdropPath
                    val imagePoster = series.posterPath
                    // Here I check if imageBackground is different from null and if it is null charge the imagePoster
                    if (imageBackground != null){
                        Glide.with(this@SearchItemDetailsActivity)
                            .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_BACKDROP + imageBackground)
                            .into(imageViewBackDrop)
                    }else{
                        Glide.with(this@SearchItemDetailsActivity)
                            .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + imagePoster)
                            .into(imageViewItemSearchDetailsPoster)
                    }

                    // Here I do the same process but in reverse
                    if (imagePoster != null){
                        Glide.with(this@SearchItemDetailsActivity)
                            .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + imagePoster)
                            .into(imageViewItemSearchDetailsPoster)
                    }else{
                        Glide.with(this@SearchItemDetailsActivity)
                            .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_BACKDROP + imageBackground)
                            .into(imageViewBackDrop)
                    }
                }

                override fun onError() {
                    Log.d("SearchItemDetailsActTV", "Error data Series And Anime")
                    toast("Please check your internet connection")
                }

            })
    }

    private fun getSeriesContentForID(callback: OnGetSeriesCallback){
        val extrasIdSeries = intent.extras.getInt("id")
        Log.d("SearchItemDetailsActTV", "Id: $extrasIdSeries")
        searchViewModel.getSeriesAndAnime(extrasIdSeries, callback)
    }




}
