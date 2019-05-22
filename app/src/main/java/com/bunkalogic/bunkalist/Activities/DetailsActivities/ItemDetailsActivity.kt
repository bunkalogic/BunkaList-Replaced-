package com.bunkalogic.bunkalist.Activities.DetailsActivities

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Dialog.AddListDialog
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.Movie
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.Series
import com.bunkalogic.bunkalist.Retrofit.Response.Trailer
import com.bunkalogic.bunkalist.ViewModel.ViewModelSearch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.search_item_full_details.*
import org.jetbrains.anko.toast
import com.bumptech.glide.request.RequestOptions
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.bunkalogic.bunkalist.Retrofit.Response.Genre
import android.text.TextUtils
import com.bunkalogic.bunkalist.Adapters.CastPersonAdapter
import com.bunkalogic.bunkalist.Adapters.CrewPersonAdapter
import com.bunkalogic.bunkalist.Adapters.RecommedationsMoviesAdapter
import com.bunkalogic.bunkalist.Adapters.RecommedationsSeriesAdapter
import com.bunkalogic.bunkalist.Retrofit.Callback.*
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import com.bunkalogic.bunkalist.Retrofit.Response.People.Cast
import com.bunkalogic.bunkalist.Retrofit.Response.People.Crew
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResultSeries
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import org.jetbrains.anko.activityManager


class ItemDetailsActivity : AppCompatActivity() {

    private lateinit var searchViewModel: ViewModelSearch


    private lateinit var toolbar: Toolbar
    lateinit var mAdView : AdView

    private lateinit var AdapterMovieRec: RecommedationsMoviesAdapter
    private lateinit var AdapterSeriesRec: RecommedationsSeriesAdapter
    private lateinit var AdapterCast: CastPersonAdapter
    private lateinit var AdapterCrew: CrewPersonAdapter

    // I use these 2 variables to collect the data correctly if it is an anime
    val bundle = Bundle()
    var isAnime : Boolean = false


    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser



    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.search_item_full_details)
        super.onCreate(savedInstanceState)
        setUpToolbarTitle()
        addBannerAds()

        //instantiate the ViewModel
        searchViewModel = ViewModelProviders.of(this).get(ViewModelSearch::class.java)
        setUpCurrentUser()
        isMovieOrSerie()
        onClick()
    }

    //initializing the banner in this activity
    private fun addBannerAds(){
        mAdView = findViewById(R.id.adViewBannerItemDetails)
        // Ad request to show on the banner
        val adRequest = AdRequest.Builder().build()
        // Associate the request to the banner
        mAdView.loadAd(adRequest)
    }


    // You can select the title of the toolbar
    private fun setUpToolbarTitle(){
        toolbar = findViewById(R.id.toolbarFullDetails)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val extrasTitle = intent.extras?.getString("title")
        val extrasName = intent.extras?.getString("name")

        if (extrasTitle == null){
            supportActionBar!!.title = extrasName
        }else{
            supportActionBar!!.title = extrasTitle
        }

    }


    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    // check if it is a series or a film and around it loads a function or another
    private fun isMovieOrSerie(){
        val type = "tv"
        val extrasType = intent.extras?.getString("type")
        Log.d("SearchItemDetailsAct", "type: $extrasType")

        if (extrasType == type){
            isSerieAndAnime()
            getSeriesCredits()
            getTrailersSeries()
            getSeriesRecommendations()
        }else{
            isMovie()
            getMoviesCredits()
            getTrailersMovies()
            getMoviesRecommendations()
        }
    }

    // is responsible for collecting the Id of the film and filling the function of the ViewModel
    private fun getMovieContentForID(callback: OnGetMovieCallback){
        val extrasIdMovies = intent.extras?.getInt("id")
        Log.d("SearchItemDetailsActMov", "Id: $extrasIdMovies")
        searchViewModel.getMovie(extrasIdMovies!!, callback)
    }
    // is responsible for filling the layout with the elements obtained from the API
    private fun isMovie(){
        getMovieContentForID(object: OnGetMovieCallback {
            override fun onSuccess(movie: Movie) {
                textViewTitleDetails.text = movie.title

                textViewLabelDate.visibility = View.VISIBLE
                textViewDateRelease.text = movie.releaseDate

                summaryLabel.visibility = View.VISIBLE
                textViewSearchDetailsAllDescription.text = movie.overview
                textViewDetailsRating.text = movie.voteAverage.toString()

                textViewLabelGenres.visibility = View.VISIBLE
                ListGenresMovies(movie)

                val imageBackground = movie.backdropPath
                val imagePoster = movie.posterPath


                // It is responsible for collecting all necessary information to verify what type it is and add it to the list
                val id = movie.id
                val type = "movie"
                val title = movie.title.toString()

                val bundle = Bundle()

                bundle.putInt("id", id!!)
                bundle.putString("title", title)
                bundle.putString("type", type)



                buttonSearchItemDetailsAddInYourList.setOnClickListener {

                    val dialog = AddListDialog()
                    dialog.arguments = bundle
                    val manager = supportFragmentManager.beginTransaction()
                    dialog.show(manager, null)

                }

                // Here I check if imageBackground is different from null and if it is null charge the imagePoster
                if (imageBackground != null){
                    Glide.with(this@ItemDetailsActivity)
                        .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_BACKDROP + imageBackground)
                        .into(imageViewBackDrop)
                }else{
                    Glide.with(this@ItemDetailsActivity)
                        .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + imagePoster)
                        .into(imageViewBackDrop)
                }
            }

            override fun onError() {
                Log.d("SearchItemDetailsActMov", "Error data Movie")
                toast("Please check your internet connection")
            }

        })
    }

    // is responsible for collecting the Id of the film and filling the function of the ViewModel
    private fun getMovieTrailersContentForID(callback: OnGetTrailersCallback){
        val extrasIdMovies = intent.extras?.getInt("id")
        searchViewModel.getTrailersMovies(extrasIdMovies!!, callback)
    }

    // is responsible for collecting the key to create the YouTube URL and show the video image
    private fun getTrailersMovies(){
        getMovieTrailersContentForID(object : OnGetTrailersCallback {
            override fun onSuccess(trailers: List<Trailer>) {
              trailersLabel.visibility = View.VISIBLE
                LinearTrailers.removeAllViews()

                for (trailer in trailers) {
                    val parent = layoutInflater.inflate(R.layout.thumbnail_trailer, LinearTrailers, false)
                    val thumbnail = parent.findViewById<ImageView>(R.id.thumbnail)
                    thumbnail.requestLayout()
                    thumbnail.setOnClickListener{
                        showTrailer(String.format(Constans.YOUTUBE_VIDEO_URL, trailer.key))
                    }
                    Glide.with(this@ItemDetailsActivity)
                        .load(String.format(Constans.YOUTUBE_THUMBNAIL_URL, trailer.key))
                        .apply(RequestOptions.placeholderOf(R.color.colorPrimary).centerCrop())
                        .into(thumbnail)
                    LinearTrailers.addView(parent)
                }

            }

            override fun onError() {
                trailersLabel.visibility = View.GONE
            }

        })
    }

    // is responsible for collecting the Id of the film and filling the function of the ViewModel
    private fun getMovieRecommendationsContentForID(callback: OnGetListMoviesCallback){
        val extrasIdMovies = intent.extras?.getInt("id")
        searchViewModel.getMovieRecommendations(extrasIdMovies!!, callback)
    }


    // It is responsible for collecting the recommended films from the movie Id
    private fun getMoviesRecommendations(){
        getMovieRecommendationsContentForID(object :
            OnGetListMoviesCallback {
            override fun onSuccess(page: Int, movies: List<ResultMovie>) {
                recommendationsLabel.visibility = View.VISIBLE
                RecyclerRecommendations.removeAllViews()

                val layoutManager = LinearLayoutManager(this@ItemDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
                AdapterMovieRec = RecommedationsMoviesAdapter(this@ItemDetailsActivity, movies)


                RecyclerRecommendations.layoutManager = layoutManager
                RecyclerRecommendations.setHasFixedSize(true)
                RecyclerRecommendations.itemAnimator = DefaultItemAnimator()
                RecyclerRecommendations.adapter = AdapterMovieRec

            }

            override fun onError() {
                recommendationsLabel.visibility = View.GONE

            }
        })
    }

    // is responsible for collecting the Id of the film and filling the function of the ViewModel
    private fun getMovieCreditsContentForID(callback: OnGetPeopleCallback){
        val extrasIdMovies = intent.extras?.getInt("id")
        searchViewModel.getPeopleMovies(extrasIdMovies!!, callback)
    }

    // It is responsible for collecting all the people who work in said oeuvre
    private fun getMoviesCredits(){
        getMovieCreditsContentForID(object : OnGetPeopleCallback {
            override fun onSuccess(crew: List<Crew>, cast: List<Cast>){

                CastLabel.visibility = View.VISIBLE
                RecyclerCast.removeAllViews()

                val layoutManagerCast = LinearLayoutManager(this@ItemDetailsActivity, LinearLayoutManager.HORIZONTAL, false)

                AdapterCast = CastPersonAdapter(this@ItemDetailsActivity, cast)

                RecyclerCast.layoutManager = layoutManagerCast
                RecyclerCast.setHasFixedSize(true)
                RecyclerCast.itemAnimator = DefaultItemAnimator()
                RecyclerCast.adapter = AdapterCast

                CrewLabel.visibility = View.VISIBLE
                RecyclerCrew.removeAllViews()

                val layoutManagerCrew = LinearLayoutManager(this@ItemDetailsActivity, LinearLayoutManager.HORIZONTAL, false)

                AdapterCrew = CrewPersonAdapter(this@ItemDetailsActivity, crew)

                RecyclerCrew.layoutManager = layoutManagerCrew
                RecyclerCrew.setHasFixedSize(true)
                RecyclerCrew.itemAnimator = DefaultItemAnimator()
                RecyclerCrew.adapter = AdapterCrew




            }

            override fun onError() {
                CastLabel.visibility = View.GONE
                CrewLabel.visibility = View.GONE
            }

        })
    }



    // is responsible for collecting the Id of the series and filling the function of the ViewModel
    private fun getSeriesContentForID(callback: OnGetSeriesCallback){
        val extrasIdSeries = intent.extras?.getInt("id")
        preferences.itemID = extrasIdSeries!!
        Log.d("SearchItemDetailsActTV", "Id: $extrasIdSeries")
        searchViewModel.getSeriesAndAnime(extrasIdSeries, callback)
    }
    // is responsible for filling the layout with the elements obtained from the API
    private fun isSerieAndAnime(){
            getSeriesContentForID(object : OnGetSeriesCallback {
                override fun onSuccess(series: Series) {
                    ListGenresSeriesAndAnime(series)
                    textViewLabelGenres.visibility = View.VISIBLE


                        textViewTitleDetails.text = series.name

                        textViewLabelDate.visibility = View.VISIBLE
                        textViewDateRelease.text = series.firstAirDate

                        summaryLabel.visibility = View.VISIBLE
                        textViewSearchDetailsAllDescription.text = series.overview

                        textViewDetailsRating.text = series.voteAverage.toString()

                        // It is responsible for collecting all necessary information to verify what type it is and add it to the list
                        val id = series.id
                        val type = "tv"
                        val title = series.name.toString()

                        bundle.putInt("id", id!!)
                        bundle.putString("title", title)
                        bundle.putString("type", type)


                        // Load images
                        val imageBackground = series.backdropPath
                        val imagePoster = series.posterPath
                        // Here I check if imageBackground is different from null and if it is null charge the imagePoster
                        if (imageBackground != null){
                            Glide.with(this@ItemDetailsActivity)
                                .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_BACKDROP + imageBackground)
                                .into(imageViewBackDrop)
                        }else{
                            Glide.with(this@ItemDetailsActivity)
                                .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + imagePoster)
                                .into(imageViewBackDrop)
                        }



                }

                override fun onError() {
                    Log.d("SearchItemDetailsActTV", "Error data Series And Anime")
                    toast("Please check your internet connection")
                }

            })
    }

    // is responsible for collecting the Id of the series and filling the function of the ViewModel
    private fun getSeriesTrailersContentForID(callback: OnGetTrailersCallback){
        val extrasIdSeries = intent.extras?.getInt("id")
        searchViewModel.getTrailersSeries(extrasIdSeries!!, callback)
    }

    // is responsible for collecting the key to create the YouTube URL and show the video image
    private fun getTrailersSeries(){
        getSeriesTrailersContentForID(object : OnGetTrailersCallback {
            override fun onSuccess(trailers: List<Trailer>) {
                trailersLabel.visibility = View.VISIBLE
                LinearTrailers.removeAllViews()

                for (trailer in trailers) {
                    val parent = layoutInflater.inflate(R.layout.thumbnail_trailer, LinearTrailers, false)
                    val thumbnail = parent.findViewById<ImageView>(R.id.thumbnail)
                    thumbnail.requestLayout()
                    thumbnail.setOnClickListener{
                        showTrailer(String.format(Constans.YOUTUBE_VIDEO_URL, trailer.key))
                    }
                    Glide.with(this@ItemDetailsActivity)
                        .load(String.format(Constans.YOUTUBE_THUMBNAIL_URL, trailer.key))
                        .apply(RequestOptions.placeholderOf(R.color.colorPrimary).centerCrop())
                        .into(thumbnail)
                    LinearTrailers.addView(parent)
                }

            }

            override fun onError() {
                trailersLabel.visibility = View.GONE
            }

        })
    }

    // is responsible for collecting the Id of the film and filling the function of the ViewModel
    private fun getSeriesRecommendationsContentForID(callback: OnGetListSeriesCallback){
        val extrasIdSeries = intent.extras?.getInt("id")
        searchViewModel.getSeriesAndAnimeRecommendations(extrasIdSeries!!, callback)
    }


    // It is responsible for collecting the recommended films from the movie Id
    private fun getSeriesRecommendations(){
        getSeriesRecommendationsContentForID(object :
            OnGetListSeriesCallback {
            override fun onSuccess(page: Int, series: List<ResultSeries>) {
                recommendationsLabel.visibility = View.VISIBLE
                RecyclerRecommendations.removeAllViews()

                val layoutManager = LinearLayoutManager(this@ItemDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
                AdapterSeriesRec = RecommedationsSeriesAdapter(this@ItemDetailsActivity, series)

                RecyclerRecommendations.layoutManager = layoutManager
                RecyclerRecommendations.setHasFixedSize(true)
                RecyclerRecommendations.itemAnimator = DefaultItemAnimator()
                RecyclerRecommendations.adapter = AdapterSeriesRec


            }

            override fun onError() {
                recommendationsLabel.visibility = View.GONE

            }
        })
    }

    // is responsible for collecting the Id of the film and filling the function of the ViewModel
    private fun getSeriesCreditsContentForID(callback: OnGetPeopleCallback){
        val extrasIdSeries = intent.extras?.getInt("id")
        searchViewModel.getPeopleSeries(extrasIdSeries!!, callback)
    }

    // It is responsible for collecting all the people who work in said oeuvre
    private fun getSeriesCredits(){
        getSeriesCreditsContentForID(object : OnGetPeopleCallback {
            override fun onSuccess(crew: List<Crew>, cast: List<Cast>){
                CastLabel.visibility = View.VISIBLE
                RecyclerCast.removeAllViews()

                val layoutManagerCast = LinearLayoutManager(this@ItemDetailsActivity, LinearLayoutManager.HORIZONTAL, false)

                AdapterCast = CastPersonAdapter(this@ItemDetailsActivity, cast)

                RecyclerCast.layoutManager = layoutManagerCast
                RecyclerCast.setHasFixedSize(true)
                RecyclerCast.itemAnimator = DefaultItemAnimator()
                RecyclerCast.adapter = AdapterCast

                CrewLabel.visibility = View.VISIBLE
                RecyclerCrew.removeAllViews()

                val layoutManagerCrew = LinearLayoutManager(this@ItemDetailsActivity, LinearLayoutManager.HORIZONTAL, false)

                AdapterCrew = CrewPersonAdapter(this@ItemDetailsActivity, crew)

                RecyclerCrew.layoutManager = layoutManagerCrew
                RecyclerCrew.setHasFixedSize(true)
                RecyclerCrew.itemAnimator = DefaultItemAnimator()
                RecyclerCrew.adapter = AdapterCrew


            }

            override fun onError() {
                CastLabel.visibility = View.GONE
                CrewLabel.visibility = View.GONE
            }

        })
    }




    // Is responsible for collecting the list of movies genres
    private fun ListGenresMovies(movie: Movie){
        searchViewModel.getGenresMovies(object : OnGetGenresCallback {
            override fun onSuccess(genres: List<Genre>) {
                if (movie.genres != null){
                    val currentGenres: ArrayList<String> = ArrayList()
                    for (genre in movie.genres!!) {
                        currentGenres.add(genre.name!!)
                    }
                    textViewLabelGenres.visibility = View.VISIBLE
                    textViewDetailsGenres.text = TextUtils.join(" - ", currentGenres)
                }
            }

            override fun onError() {
                Log.d("SearchItemDetailsAct", "Error Connection")
                textViewLabelGenres.visibility = View.GONE
            }

        })
    }

    // Is responsible for collecting the list of series genres
    private fun ListGenresSeriesAndAnime(series: Series){
        searchViewModel.getGenresSeries(object : OnGetGenresCallback {
            override fun onSuccess(genres: List<Genre>) {
                if (series.genres != null){
                    val currentGenres: ArrayList<String> = ArrayList()
                    val currentGenresId: ArrayList<Int> = ArrayList()

                    for (genre in series.genres!!) {
                        currentGenres.add(genre.name!!)
                        currentGenresId.add(genre.id!!)
                    }
                    isAnime = currentGenresId.filter { it == 16 }.any()
                    Log.d("SearchItemDetailsActTV", "is Anime: $isAnime")

                    textViewLabelGenres.visibility = View.VISIBLE
                    textViewDetailsGenres.text = TextUtils.join(" - ", currentGenres)
                }
            }

            override fun onError() {
                Log.d("SearchItemDetailsAct", "Error Connection")
                textViewLabelGenres.visibility = View.GONE
            }

        })
    }


    private fun onClick(){
        buttonSearchItemDetailsAddInYourList.setOnClickListener {
            // I add this here so that the boolean isAnime is picked up correctly
            bundle.putBoolean("anime", isAnime)

            val dialog = AddListDialog()
            dialog.arguments = bundle
            val manager = supportFragmentManager.beginTransaction()
            dialog.show(manager, null)

        }
    }

    // Is responsible for launching the YouTube App
    private fun showTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
