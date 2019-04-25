package com.bunkalogic.bunkalist.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Adapters.TopListMoviesAdapter
import com.bunkalogic.bunkalist.Adapters.TopListSeriesAdapter
import com.bunkalogic.bunkalist.Others.Constans

import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.OnGetListMoviesCallback
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import com.bunkalogic.bunkalist.ViewModel.ViewModelSearch
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.fragment_list_movie.view.*
import org.jetbrains.anko.support.v4.toast


class ListMovieFragment : Fragment() {

    private lateinit var _view: View
    lateinit var mAdView : AdView

    private var typeList = 0

    var adapter: TopListMoviesAdapter? = null

    private lateinit var searchViewModel: ViewModelSearch
    //private var seriesList: ArrayList<ResultSeries>? = null

    private var isFetchingSeries: Boolean = false
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeList = it.getInt(Constans.TYPE_LIST_TOP)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view =  inflater.inflate(R.layout.fragment_list_movie, container, false)
        addBannerAds()


        return _view
    }

    //initializing the banner in this activity
    private fun addBannerAds(){
        mAdView = _view.findViewById(R.id.adViewBannerFragmentListTopMovies)
        // Ad request to show on the banner
        val adRequest = AdRequest.Builder().build()
        // Associate the request to the banner
        mAdView.loadAd(adRequest)
    }


    private fun whatTypeListIs(){
        if (typeList == Constans.Popular_LIST){
            getPopularMovies(currentPage + 1)
        }else if (typeList == Constans.Rated_LIST){
            getRatedMovies(currentPage + 1)
        }else if (typeList == Constans.Upcoming_LIST){

        }
    }

    private fun setUpOnScrollListener() {
        val manager = LinearLayoutManager(context)
        _view.recyclerAllListTopMovies.layoutManager = manager

        _view.recyclerAllListTopMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = manager.itemCount
                val visibleItemCount = manager.childCount
                val firstVisibleItem = manager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetchingSeries) {
                        //whatTypeListIs()

                    }
                }
            }
        })
    }

    private fun initializeListMoviesPopular(page: Int, callback: OnGetListMoviesCallback){
        searchViewModel = ViewModelProviders.of(activity!!).get(ViewModelSearch::class.java)
        searchViewModel.getMoviesPopular(page, callback)
    }

    private fun initializeListMoviesRated(page: Int, callback: OnGetListMoviesCallback){
        searchViewModel = ViewModelProviders.of(activity!!).get(ViewModelSearch::class.java)
        searchViewModel.getMoviesRated(page, callback)
    }




    private fun getPopularMovies(page: Int){
        isFetchingSeries = true
        initializeListMoviesPopular(page, object :OnGetListMoviesCallback{
            override fun onSuccess(page: Int, movies: List<ResultMovie>) {
                if (adapter == null){
                    adapter = TopListMoviesAdapter(context!!, movies as ArrayList)
                    _view.recyclerAllListTopMovies.adapter = adapter
                }else{
                    adapter!!.appendMovies(movies)
                }
                currentPage = page
                isFetchingSeries = false
            }

            override fun onError() {
                toast("Please check your internet connection")
            }

        })
    }

    private fun getRatedMovies(page: Int){
        isFetchingSeries = true
        initializeListMoviesRated(page, object :OnGetListMoviesCallback{
            override fun onSuccess(page: Int, movies: List<ResultMovie>) {
                if (adapter == null){
                    adapter = TopListMoviesAdapter(context!!, movies as ArrayList)
                    _view.recyclerAllListTopMovies.adapter = adapter
                }else{
                    adapter!!.appendMovies(movies)
                }
                currentPage = page
                isFetchingSeries = false
            }

            override fun onError() {
                toast("Please check your internet connection")
            }


        })
    }







    companion object {
        @JvmStatic
        fun newInstance(typeList: Int) =
            ListMovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constans.TYPE_LIST_TOP, typeList)
                }
            }
    }



}
