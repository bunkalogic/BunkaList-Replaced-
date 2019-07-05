package com.bunkalogic.bunkalist.Activities.DetailsActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bunkalogic.bunkalist.Adapters.TopListMoviesAdapter
import com.bunkalogic.bunkalist.Adapters.TopListSeriesAdapter
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetListMoviesCallback
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetListSeriesCallback
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetSeriesListFilterCallback
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResultSeries
import com.bunkalogic.bunkalist.ViewModel.ViewModelAPItmdb
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_list_tops.*
import org.jetbrains.anko.toast

class ListTopsActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var mAdView : AdView

    var adapterMovies: TopListMoviesAdapter? = null
    var adapterSeries: TopListSeriesAdapter? = null

    private lateinit var searchViewModel: ViewModelAPItmdb

    private var isFetching: Boolean = false
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_tops)
        searchViewModel = ViewModelProviders.of(this).get(ViewModelAPItmdb::class.java)
        addBannerAds()
        setUpToolbar()
        whatTypeListIs()
        setUpOnScrollListener()

    }

    //initializing the banner in this activity
    private fun addBannerAds(){
        mAdView = findViewById(R.id.adViewBannerFragmentListTopMovies)
        // Ad request to show on the banner
        val adRequest = AdRequest.Builder().build()
        // Associate the request to the banner
        mAdView.loadAd(adRequest)
    }

    private fun setUpToolbar(){
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val extrasTitle = intent.extras?.getString("title")

        supportActionBar!!.title = extrasTitle
    }

    private fun setUpOnScrollListener() {
        val manager = androidx.recyclerview.widget.LinearLayoutManager(this)
        RecyclerViewTops.layoutManager = manager

        RecyclerViewTops.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = manager.itemCount
                val visibleItemCount = manager.childCount
                val firstVisibleItem = manager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetching) {
                        whatTypeListIs()
                    }
                }
            }
        })
    }

    private fun whatTypeListIs(){
        val extrasTopId = intent.extras?.getInt("idTops")

        when(extrasTopId) {
            Constans.Popular_LIST_Movies -> {
                getMovieList(Constans.Popular_LIST, currentPage + 1)
            }

            Constans.Rated_LIST_Movies -> {
                getMovieList(Constans.Rated_LIST, currentPage + 1)
            }

            Constans.Upcoming_LIST_Movies -> {
                getMovieList(Constans.Upcoming_LIST, currentPage + 1)
            }

            Constans.Popular_LIST_Series -> {
                getSeriesList(Constans.Popular_LIST, currentPage + 1)
            }

            Constans.Rated_LIST_Series -> {
                getSeriesList(Constans.Rated_LIST, currentPage + 1)
            }

            Constans.Upcoming_LIST_Series -> {
                getSeriesList(Constans.Upcoming_LIST, currentPage + 1)
            }

            Constans.Popular_LIST_Anime -> {
                isAnimePopular(currentPage + 1)
            }

            Constans.Rated_LIST_Anime -> {
                isAnimeRated(currentPage + 1)
            }

            Constans.Upcoming_LIST_Anime -> {
                isAnimeUpcoming(currentPage + 1)
            }

        }

    }

    // Is responsible for showing the lists of the movies
    private fun getMovieList(typeList: String, page: Int){
        isFetching = true
        searchViewModel.getMoviesList(page, typeList, object :
            OnGetListMoviesCallback {
            override fun onSuccess(page: Int, movies: List<ResultMovie>) {
                if (adapterMovies == null){
                    adapterMovies = TopListMoviesAdapter(this@ListTopsActivity, movies as ArrayList)
                    RecyclerViewTops.adapter = adapterMovies
                }else{
                    if (page == 1){
                        adapterMovies?.clearList()
                    }
                    adapterMovies?.appendMovies(movies)
                }

                currentPage = page
                isFetching = false
            }
            override fun onError() {
                toast("Please check your internet connection")
            }

        })
    }

    // Is responsible for showing the lists of the series
    private fun getSeriesList(typeList: String, page: Int){
        isFetching = true
        searchViewModel.getSeriesList(page, typeList, object :
            OnGetListSeriesCallback {
            override fun onSuccess(page: Int, series: List<ResultSeries>) {
                if (adapterSeries == null){
                    adapterSeries = TopListSeriesAdapter(this@ListTopsActivity, series as ArrayList)
                    RecyclerViewTops.adapter = adapterSeries
                }else{
                    if (page == 1){
                        adapterSeries?.clearList()
                    }
                    adapterSeries?.appendSeries(series)
                }

                currentPage = page
                isFetching = false
            }
            override fun onError() {
                toast("Please check your internet connection")
            }

        })
    }


    private fun getAnimeListPopular(page: Int,callback: OnGetSeriesListFilterCallback) {
        //pass the filtering data
        searchViewModel = ViewModelProviders.of(this).get(ViewModelAPItmdb::class.java)
        searchViewModel.getTopsAnime(callback, Constans.filter_search_sort_populary_desc, page, Constans.filter_search_genres_movies_animation.toString(), -1, 4)



    }

    // Responsible for displaying an anime list Popular
    private fun isAnimePopular(page: Int){
        getAnimeListPopular(page, object : OnGetSeriesListFilterCallback{
            override fun onSuccess(page: Int, series: List<ResultSeries>) {
                if (adapterSeries == null){
                    adapterSeries = TopListSeriesAdapter(this@ListTopsActivity, series as ArrayList)
                    RecyclerViewTops.adapter = adapterSeries
                }else{
                    if (page == 1){
                        adapterSeries?.clearList()
                    }
                    adapterSeries?.appendSeries(series)
                }

                currentPage = page
                isFetching = false
            }

            override fun onError() {
                toast("Please check your internet connection")
            }

        })
    }


    private fun getAnimeListRated(page: Int,callback: OnGetSeriesListFilterCallback) {
        //pass the filtering data
        searchViewModel = ViewModelProviders.of(this).get(ViewModelAPItmdb::class.java)
        searchViewModel.getTopsAnime(callback, Constans.filter_search_sort_voted_average_desc, page, Constans.filter_search_genres_movies_animation.toString(), -1, 4)



    }

    // Responsible for displaying an anime list Rated
    private fun isAnimeRated(page: Int){
        getAnimeListRated(page, object : OnGetSeriesListFilterCallback{
            override fun onSuccess(page: Int, series: List<ResultSeries>) {
                if (adapterSeries == null){
                    adapterSeries = TopListSeriesAdapter(this@ListTopsActivity, series as ArrayList)
                    RecyclerViewTops.adapter = adapterSeries
                }else{
                    if (page == 1){
                        adapterSeries?.clearList()
                    }
                    adapterSeries?.appendSeries(series)
                }

                currentPage = page
                isFetching = false
            }

            override fun onError() {
                toast("Please check your internet connection")
            }

        })
    }



    private fun getAnimeListUpcoming(page: Int,callback: OnGetSeriesListFilterCallback) {
        //pass the filtering data
        searchViewModel = ViewModelProviders.of(this).get(ViewModelAPItmdb::class.java)
        searchViewModel.getSeriesFilters(callback, Constans.filter_search_sort_series_first_air_date_desc, page, Constans.filter_search_genres_movies_animation.toString(), 2019)



    }

    // Responsible for displaying an anime list Upcoming
    private fun isAnimeUpcoming(page: Int){
        getAnimeListUpcoming(page, object : OnGetSeriesListFilterCallback{
            override fun onSuccess(page: Int, series: List<ResultSeries>) {
                if (adapterSeries == null){
                    adapterSeries = TopListSeriesAdapter(this@ListTopsActivity, series as ArrayList)
                    RecyclerViewTops.adapter = adapterSeries
                }else{
                    if (page == 1){
                        adapterSeries?.clearList()
                    }
                    adapterSeries?.appendSeries(series)
                }

                currentPage = page
                isFetching = false
            }

            override fun onError() {
                toast("Please check your internet connection")
            }

        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
