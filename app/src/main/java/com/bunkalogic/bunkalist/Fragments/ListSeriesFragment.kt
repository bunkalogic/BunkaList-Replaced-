package com.bunkalogic.bunkalist.Fragments


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Adapters.TopListSeriesAdapter
import com.bunkalogic.bunkalist.Others.Constans

import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetListSeriesCallback
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResultSeries
import com.bunkalogic.bunkalist.ViewModel.ViewModelAPItmdb
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.fragment_list_series.view.*
import org.jetbrains.anko.support.v4.toast


class ListSeriesFragment : androidx.fragment.app.Fragment() {

    private lateinit var _view: View
    lateinit var mAdView : AdView

    var adapter: TopListSeriesAdapter? = null

    private lateinit var searchViewModel: ViewModelAPItmdb
    //private var seriesList: ArrayList<ResultSeries>? = null

    private var typeList = 0

    private var isFetchingSeries: Boolean = false
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProviders.of(activity!!).get(ViewModelAPItmdb::class.java)

        arguments?.let {
            typeList = it.getInt(Constans.TYPE_LIST_TOP_SERIES)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
         _view = inflater.inflate(R.layout.fragment_list_series, container, false)
        addBannerAds()
        whatTypeListIs()
        setUpOnScrollListener()

        return _view
    }

    //initializing the banner in this activity
    private fun addBannerAds(){
        mAdView = _view.findViewById(R.id.adViewBannerFragmentListTopSeries)
        // Ad request to show on the banner
        val adRequest = AdRequest.Builder().build()
        // Associate the request to the banner
        mAdView.loadAd(adRequest)
    }

    private fun whatTypeListIs(){
        when (typeList) {
            Constans.Popular_LIST_Series ->{
                getPopularSeries(currentPage + 1)
            }
            Constans.Rated_LIST_Series ->{
                getRatedSeries(currentPage + 1)
            }
            Constans.Upcoming_LIST_Series -> {
                getUpcomingSeries(currentPage + 1)
            }
        }
    }

    private fun setUpOnScrollListener() {
        val manager = androidx.recyclerview.widget.LinearLayoutManager(context)
        _view.recyclerAllListTopSeries.layoutManager = manager

        _view.recyclerAllListTopSeries.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = manager.itemCount
                val visibleItemCount = manager.childCount
                val firstVisibleItem = manager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetchingSeries) {
                        whatTypeListIs()
                    }
                }
            }
        })
    }

    private fun initializeListSeriesPopular(page: Int, callback: OnGetListSeriesCallback){
        searchViewModel.getSeriesPopular(page, callback)
    }

    private fun initializeListSeriesRated(page: Int, callback: OnGetListSeriesCallback){
        searchViewModel.getSeriesRated(page, callback)
    }

    private fun initializeListSeriesUpcoming(page: Int, callback: OnGetListSeriesCallback){
        searchViewModel.getSeriesUpcoming(page, callback)
    }


    private fun getPopularSeries(page: Int){
        isFetchingSeries = true
        initializeListSeriesPopular(page, object : OnGetListSeriesCallback {
            override fun onSuccess(page: Int, series: List<ResultSeries>) {
                if (adapter == null){
                    adapter = TopListSeriesAdapter(context!!, series as ArrayList)
                    _view.recyclerAllListTopSeries.adapter = adapter
                }else{
                    if (page == 1){
                        adapter?.clearList()
                    }
                    adapter?.appendSeries(series)
                }
                currentPage = page
                isFetchingSeries = false
            }

            override fun onError() {
                toast("Please check your internet connection")
            }

        })
    }

    private fun getRatedSeries(page: Int){
        isFetchingSeries = true
        initializeListSeriesRated(page, object : OnGetListSeriesCallback {
            override fun onSuccess(page: Int, series: List<ResultSeries>) {
                if (adapter == null){
                    adapter = TopListSeriesAdapter(context!!, series as ArrayList)
                    _view.recyclerAllListTopSeries.adapter = adapter
                }else{
                    if (page == 1){
                        adapter?.clearList()
                    }
                    adapter?.appendSeries(series)
                }
                currentPage = page
                isFetchingSeries = false
            }

            override fun onError() {
                toast("Please check your internet connection")
            }


        })
    }

    private fun getUpcomingSeries(page: Int){
        isFetchingSeries = true
        initializeListSeriesUpcoming(page, object : OnGetListSeriesCallback {
            override fun onSuccess(page: Int, series: List<ResultSeries>) {
                if (adapter == null){
                    adapter = TopListSeriesAdapter(context!!, series as ArrayList)
                    _view.recyclerAllListTopSeries.adapter = adapter
                }else{
                    if (page == 1){
                        adapter?.clearList()
                    }
                    adapter?.appendSeries(series)
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
            ListSeriesFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constans.TYPE_LIST_TOP_SERIES, typeList)
                }
            }
    }


}
