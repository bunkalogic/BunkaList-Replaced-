package com.bunkalogic.bunkalist.Fragments



import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Adapters.SearchItemAdapter
import com.bunkalogic.bunkalist.Adapters.SortListMoviesAdapter
import com.bunkalogic.bunkalist.Adapters.SortListSeriesAdapter
import com.bunkalogic.bunkalist.Fragments.FabFilter.FabFilterSearchFragment
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetMovieListFilterCallback
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetSearchCallback
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetSeriesListFilterCallback
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchAll
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResultSeries
import com.bunkalogic.bunkalist.ViewModel.ViewModelAPItmdb
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.jetbrains.anko.support.v4.toast
import java.time.Year


/**
 *  Created by @author Naim Dridi on 25/02/19
 */


class SearchFragment : androidx.fragment.app.Fragment() {

    private lateinit var _view: View
    lateinit var mAdView : AdView

    private lateinit var adapter: SearchItemAdapter
    private var adapterSortMovies: SortListMoviesAdapter? = null
    private var adapterSortSeries: SortListSeriesAdapter? = null

    private var isFetching: Boolean = false
    private var currentPage = 0

    private lateinit var searchViewModel: ViewModelAPItmdb
    private var searchList: List<ResultSearchAll>? = null

    // variables the sort
    var applied_sort: ArrayMap<String, MutableList<Any>> = ArrayMap()
    var whatTypeIs = 0
    var listSortGenres : MutableList<Any> = mutableListOf()
    var sortYear = 0
    var whatSortIs = ""
    //new list for series genres
    var listSortGenresSeriesRemove : MutableList<Any> = mutableListOf()
    var listSortGenresSeriesAdd : MutableList<Any> = mutableListOf()


    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_search, container, false)
        addBannerAds()
        setUpCurrentUser()
        setUpAdapterSearch()
        onClick()
        //getTitleOuvreFromTimelineFragment()

        return _view
    }

    //initializing the banner in this activity
    private fun addBannerAds(){
        mAdView = _view.findViewById(R.id.adViewBannerFragmentSearch)
        // Ad request to show on the banner
        val adRequest = AdRequest.Builder().build()
        // Associate the request to the banner
        mAdView.loadAd(adRequest)
    }




    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

   private fun setUpAdapterSearch(){
       val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

       adapter = SearchItemAdapter(context!!, searchList)
       _view.recyclerSearch.layoutManager = layoutManager
       _view.recyclerSearch.setHasFixedSize(true)
       _view.recyclerSearch.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()

       _view.recyclerSearch.adapter = adapter
   }

    private fun getSearchAll(callback: OnGetSearchCallback) {
        searchViewModel = ViewModelProviders.of(activity!!).get(ViewModelAPItmdb::class.java)
        val title = editTextSearch.text.toString()
        searchViewModel.getSearchAll(title, callback)


    }

    @SuppressLint("RestrictedApi")
    private fun onClick(){

        _view.editTextSearch.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if ((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    getSearchAll(object: OnGetSearchCallback {
                        override fun onSuccess(all: List<ResultSearchAll>) {
                            Log.d("FragmentSearch", "On success data")
                            if (all.isEmpty()){
                                toast("Please search again")
                            }else{
                                adapter.setData(all)
                                _view.animation_search_data_null.visibility = View.GONE
                                adapter.notifyDataSetChanged()

                            }
                        }

                        override fun onError() {
                            adapter.notifyDataSetChanged()
                            _view.animation_search_data_null.visibility = View.VISIBLE
                            toast("Please check your internet connection")
                        }
                    })
                }
                return false
            }

        })

        _view.fabSortSearch.setOnClickListener {
            _view.fabSortSearch.visibility = View.GONE
            _view.fabSortCheck.visibility = View.VISIBLE
            val dialogFabList: FabFilterSearchFragment = FabFilterSearchFragment.newInstance()
            dialogFabList.setParentFab(fabSortSearch)
            dialogFabList.show(activity?.supportFragmentManager, dialogFabList.tag)
        }

        _view.fabSortCheck.setOnClickListener {
            getSortList(Constans.applied_search_filter)
            setUpOnScrollListener()
            _view.fabSortCheck.visibility = View.GONE
            _view.fabSortClean.visibility = View.VISIBLE
        }

        _view.fabSortClean.setOnClickListener {
            //we clean all the data to avoid
            Constans.applied_search_filter.clear()
            adapterSortMovies?.clearList()
            adapterSortSeries?.clearList()
            listSortGenres.clear()
            whatTypeIs = 0
            whatSortIs = ""
            _view.fabSortClean.visibility = View.GONE
            _view.fabSortSearch.visibility = View.VISIBLE

        }
    }

    private fun getTitleOuvreFromTimelineFragment(){
        val args = arguments
        val nameOuevre = args?.getString(Constans.SEARCH_NAME)

        if (nameOuevre.toString().isEmpty()){
            Log.d("FragmentSearch", "nameOuevre is Empty")
        }else{
            searchViewModel = ViewModelProviders.of(activity!!).get(ViewModelAPItmdb::class.java)
            searchViewModel.getSearchAll(nameOuevre.toString(), object : OnGetSearchCallback{
                override fun onSuccess(all: List<ResultSearchAll>) {
                    Log.d("FragmentSearch", "On success data")
                    if (all.isEmpty()){
                        toast("Please search again")
                    }else{
                        adapter.setData(all)

                    }
                }

                override fun onError() {
                    toast("Please check your internet connection")
                }

            })
        }
    }

    // this function is in charge of checking which data has been collected by Constans.applied_search_filter and around it
    private fun getSortList(applied_sort: ArrayMap<String, MutableList<Any>>){
        if (Constans.applied_search_filter.isNotEmpty()){

            val type = context!!.getString(R.string.fab_filter_search_type)
            val genres = context!!.getString(R.string.fab_filter_search_genres)
            val year = context!!.getString(R.string.fab_filter_search_year)
            val sort = context!!.getString(R.string.fab_filter_search_sort)

            for ((key, value) in applied_sort){
                Log.d("FragmentSearch", "Key: $key, value: ${value.size}")


                // Here we check what value it is and we collect it in a variable
                when(key){
                    type -> {
                        for (types in value){
                            whatTypeIs = types.hashCode()
                        }
                    }
                    genres ->{
                        listSortGenres = value
                    }
                    year -> {
                        for (date in value){
                            sortYear = date.hashCode()
                        }
                    }
                    sort ->{
                        for (filter in value){
                            whatSortIs = filter.toString()
                        }
                    }
                }
            }
            isGenresListMoviesOrSeries(whatTypeIs, listSortGenres)
            changedSortDate(whatTypeIs, whatSortIs)
            whatType(whatTypeIs)
        }
    }

    // is responsible for checking what type is
    private fun whatType(typeId: Int){
        when (typeId) {
            Constans.filter_search_type_movie_id -> isMovie(currentPage + 1)
            Constans.filter_search_type_series_id -> isSeries(currentPage + 1)
            Constans.filter_search_type_anime_id -> isAnime(currentPage + 1)
        }
    }

    // we check what type is around it we modify the list of genres or not
    private fun isGenresListMoviesOrSeries(typeId: Int, genresList : MutableList<Any>){

        when (typeId) {
            Constans.filter_search_type_series_id -> {

                // check if the list contains the genre action or the genre adventure and replaces it with the id of those genres but of series
                if (genresList.contains(Constans.filter_search_genres_movies_action)
                    ||genresList.contains(Constans.filter_search_genres_movies_adventure)){
                    listSortGenresSeriesRemove.add(Constans.filter_search_genres_movies_action)
                    listSortGenresSeriesRemove.add(Constans.filter_search_genres_movies_adventure)
                    listSortGenresSeriesAdd.add(Constans.filter_search_genres_series_action_adventure)
                    genresList.removeAll(listSortGenresSeriesRemove)
                    genresList.addAll(listSortGenresSeriesAdd)

                }
                if (genresList.contains(Constans.filter_search_genres_movies_fantasy)
                    ||genresList.contains(Constans.filter_search_genres_movies_scince_fiction)){
                    listSortGenresSeriesRemove.add(Constans.filter_search_genres_movies_scince_fiction)
                    listSortGenresSeriesRemove.add(Constans.filter_search_genres_movies_fantasy)
                    listSortGenresSeriesAdd.add(Constans.filter_search_genres_series_scince_fiction_fantasy)
                    genresList.removeAll(listSortGenresSeriesRemove)
                    genresList.addAll(listSortGenresSeriesAdd)

                }

                if (genresList.contains(Constans.filter_search_genres_movies_war)){
                    listSortGenresSeriesRemove.add(Constans.filter_search_genres_movies_war)
                    listSortGenresSeriesAdd.add(Constans.filter_search_genres_series_war_politics)
                    genresList.removeAll(listSortGenresSeriesRemove)
                    genresList.addAll(listSortGenresSeriesAdd)

                }
            }
            Constans.filter_search_type_anime_id -> {
               if (genresList.contains(Constans.filter_search_genres_movies_animation)){
                   Log.d("FragmentSearch", "contains animation genre")
               }else{
                   genresList.add(Constans.filter_search_genres_movies_animation)
               }

                // check if the list contains the genre action or the genre adventure and replaces it with the id of those genres but of series
                if (genresList.contains(Constans.filter_search_genres_movies_action)
                    ||genresList.contains(Constans.filter_search_genres_movies_adventure)){
                    listSortGenresSeriesRemove.add(Constans.filter_search_genres_movies_action)
                    listSortGenresSeriesRemove.add(Constans.filter_search_genres_movies_adventure)
                    listSortGenresSeriesAdd.add(Constans.filter_search_genres_series_action_adventure)
                    genresList.removeAll(listSortGenresSeriesRemove)
                    genresList.addAll(listSortGenresSeriesAdd)

                }
                if (genresList.contains(Constans.filter_search_genres_movies_fantasy)
                    ||genresList.contains(Constans.filter_search_genres_movies_scince_fiction)){
                    listSortGenresSeriesRemove.add(Constans.filter_search_genres_movies_scince_fiction)
                    listSortGenresSeriesRemove.add(Constans.filter_search_genres_movies_fantasy)
                    listSortGenresSeriesAdd.add(Constans.filter_search_genres_series_scince_fiction_fantasy)
                    genresList.removeAll(listSortGenresSeriesRemove)
                    genresList.addAll(listSortGenresSeriesAdd)

                }

                if (genresList.contains(Constans.filter_search_genres_movies_war)){
                    listSortGenresSeriesRemove.add(Constans.filter_search_genres_movies_war)
                    listSortGenresSeriesAdd.add(Constans.filter_search_genres_series_war_politics)
                    genresList.removeAll(listSortGenresSeriesRemove)
                    genresList.addAll(listSortGenresSeriesAdd)

                }
            }
        }
    }

    private fun changedSortDate(typeId: Int, whatSort: String){
        when(typeId){
            Constans.filter_search_type_series_id -> {
               if (whatSort == Constans.filter_search_sort_release_date_asc){
                   whatSortIs = Constans.filter_search_sort_series_first_air_date_asc
               }
                if (whatSort == Constans.filter_search_sort_release_date_desc){
                    whatSortIs = Constans.filter_search_sort_series_first_air_date_desc
                }
            }
            Constans.filter_search_type_anime_id -> {
                if (whatSort == Constans.filter_search_sort_release_date_asc){
                    whatSortIs = Constans.filter_search_sort_series_first_air_date_asc
                }
                if (whatSort == Constans.filter_search_sort_release_date_desc){
                    whatSortIs = Constans.filter_search_sort_series_first_air_date_desc
                }
            }
        }
    }


    private fun setUpOnScrollListener(){
        val manager = androidx.recyclerview.widget.LinearLayoutManager(context)
        _view.recyclerSearch.layoutManager = manager

        _view.recyclerSearch.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = manager.itemCount
                val visibleItemCount = manager.childCount
                val firstVisibleItem = manager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetching) {
                        whatType(whatTypeIs)
                    }
                }
            }
        })
    }

    private fun isMovie(page: Int){
        isFetching = true
        getMoviesSortAll(page, object : OnGetMovieListFilterCallback{
            override fun onSuccess(page: Int, movies: List<ResultMovie>) {
                if (adapterSortMovies == null){
                    adapterSortMovies = SortListMoviesAdapter(context!!, movies as ArrayList)
                    _view.recyclerSearch.adapter = adapterSortMovies
                }else{
                    if (page == 1){
                        adapterSortMovies?.clearList()
                    }
                    adapterSortMovies?.appendMovies(movies)
                }

                currentPage = page
                isFetching = false
            }

            override fun onError() {
                toast("Please check your internet connection")
            }

        })
    }

    private fun isSeries(page: Int){
        getSeriesAndAnimeSortAll(page, object : OnGetSeriesListFilterCallback{
            override fun onSuccess(page: Int, series: List<ResultSeries>) {
                if (adapterSortSeries == null){
                    adapterSortSeries = SortListSeriesAdapter(context!!, series as ArrayList)
                    _view.recyclerSearch.adapter = adapterSortSeries
                }else{
                    if (page == 1){
                        adapterSortSeries?.clearList()
                    }
                    adapterSortSeries?.appendSeries(series)
                }

                currentPage = page
                isFetching = false
            }

            override fun onError() {
                toast("Please check your internet connection")
            }

        })
    }

    private fun isAnime(page: Int){
        getSeriesAndAnimeSortAll(page, object : OnGetSeriesListFilterCallback{
            override fun onSuccess(page: Int, series: List<ResultSeries>) {
                if (adapterSortSeries == null){
                    adapterSortSeries = SortListSeriesAdapter(context!!, series as ArrayList)
                    _view.recyclerSearch.adapter = adapterSortSeries
                }else{
                    if (page == 1){
                        adapterSortSeries?.clearList()
                    }
                    adapterSortSeries?.appendSeries(series)
                }

                currentPage = page
                isFetching = false
            }

            override fun onError() {
                toast("Please check your internet connection")
            }

        })
    }



    private fun getMoviesSortAll(page: Int, callback: OnGetMovieListFilterCallback) {
        //pass the filtering data
        val genre = listSortGenres.joinToString(separator = ",")

        searchViewModel = ViewModelProviders.of(activity!!).get(ViewModelAPItmdb::class.java)
        searchViewModel.getMoviesFilters(callback, whatSortIs, page, genre, sortYear)


    }

    private fun getSeriesAndAnimeSortAll(page: Int,callback: OnGetSeriesListFilterCallback) {
        //pass the filtering data
        val genre = listSortGenres.joinToString(separator = ",")

        searchViewModel = ViewModelProviders.of(activity!!).get(ViewModelAPItmdb::class.java)
        searchViewModel.getSeriesFilters(callback, whatSortIs, page, genre, sortYear)



    }


    fun getAppliedFilters() : ArrayMap<String, MutableList<Any>> {
        return applied_sort
    }


}
