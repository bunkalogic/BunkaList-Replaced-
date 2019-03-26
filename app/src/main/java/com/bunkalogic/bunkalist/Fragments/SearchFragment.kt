package com.bunkalogic.bunkalist.Fragments


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Adapters.SearchItemAdapter
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.MoviesOrSeriesAndAnimeClient
import com.bunkalogic.bunkalist.Retrofit.MoviesOrSeriesAndAnimeService
import com.bunkalogic.bunkalist.Retrofit.OnGetMoviesCallback
import com.bunkalogic.bunkalist.Retrofit.Response.ResponseSearchAll
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchAll
import com.bunkalogic.bunkalist.data.RepositorySearch
import com.bunkalogic.bunkalist.data.ViewModelSearch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_search_item.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 *  Created by @author Naim Dridi on 25/02/19
 */


class SearchFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var adapter: SearchItemAdapter
    private lateinit var searchViewModel: ViewModelSearch
    private lateinit var searchRepository: RepositorySearch
    var searchList: List<ResultSearchAll>? = null
    private val LANGUAGE = "en-US"

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser


    lateinit var moviesOrSeriesAndAnimeClient: MoviesOrSeriesAndAnimeClient
    lateinit var moviesOrSeriesAndAnimeService: MoviesOrSeriesAndAnimeService




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_search, container, false)
        setUpCurrentUser()
        setUpAdapter()
        retrofitInit()
        onClick()
        return _view
    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

   private fun setUpAdapter(){
       val layoutManager = LinearLayoutManager(context)

       adapter = SearchItemAdapter(activity!!, searchList)
       _view.recyclerSearch.layoutManager = layoutManager
       _view.recyclerSearch.setHasFixedSize(true)
       _view.recyclerSearch.itemAnimator = DefaultItemAnimator()

       _view.recyclerSearch.adapter = adapter
   }
    // TODO: Probar hacer la llamada con resultSearchAll y hacer cambios en el MoviesOrSeriesAndAnimeClient
    fun retrofitInit(){
        //moviesOrSeriesAndAnimeClient = MoviesOrSeriesAndAnimeClient.getInstance()
        //moviesOrSeriesAndAnimeService = moviesOrSeriesAndAnimeClient.getMoviesOrSeriesAndAnimeService()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constans.API_MOVIE_SERIES_ANIME_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.client(client)
            .build()
        moviesOrSeriesAndAnimeService = retrofit.create(MoviesOrSeriesAndAnimeService::class.java)
    }

    fun getSearchAll(callback: OnGetMoviesCallback) {
        //val data = MutableLiveData<ResponseSearchAll>()

        val title = editTextSearch.text.toString()


         moviesOrSeriesAndAnimeService
             .getSearchAll(Constans.API_KEY, LANGUAGE, title, 1, false)
             .enqueue(object : Callback<ResponseSearchAll>{

            override fun onResponse(call: Call<ResponseSearchAll>, response: Response<ResponseSearchAll>) {
                if (response.isSuccessful){
                    val searchResponse: ResponseSearchAll = response.body()!!
                    if (searchResponse.results != null){
                        callback.onSuccess(searchResponse.results!!)

                        adapter.setData(searchResponse.results!!)
                    }else{
                        Log.d("FragmentSearch", "Something has gone wrong")
                        callback.onError()
                    }
                }else{
                    Log.d("FragmentSearch", "Something has gone wrong on response.isSuccessful")
                }
            }

            override fun onFailure(call: Call<ResponseSearchAll>, t: Throwable) {
                callback.onError()
                Log.d("FragmentSearch", "Error connection")
            }
        })

        //call.enqueue(object : Callback<ResponseSearchAll> {
        //    override fun onResponse(call: Call<ResponseSearchAll>, response: Response<ResponseSearchAll>) {
        //        if (response.isSuccessful){
        //            data.value = response.body()
        //            //setUpAdapter()
        //            adapter = SearchItemAdapter(
        //                activity!!,
        //                searchList
        //            )
        //            _view.recyclerSearch.setHasFixedSize(true)
        //            _view.recyclerSearch.itemAnimator = DefaultItemAnimator()
        //            _view.recyclerSearch.adapter = adapter
        //            adapter.notifyDataSetChanged()
        //        }else{
        //            Log.d("FragmentSearch", "Something has gone wrong")
        //        }
//
        //    }
//
        //    override fun onFailure(call: Call<ResponseSearchAll>, t: Throwable) {
        //        Log.d("FragmentSearch", "Error connection")
        //    }
//
        //})


    }

    private fun onClick(){

        _view.imageViewSearch.setOnClickListener {
            getSearchAll(object: OnGetMoviesCallback{
                override fun onSuccess(all: List<ResultSearchAll>) {
                    Log.d("FragmentSearch", "On success data")

                }

                override fun onError() {
                  toast("Please check your internet connection")
                }

            })
        }
    }






  //  private fun getNameTitle(){
  //     val name = _view.editTextSearch.text.toString()
  //      searchViewModel = ViewModelProviders.of(this).get(ViewModelSearch::class.java)
  //
  //      _view.imageViewSearch.setOnClickListener {
  //          searchViewModel.getQuery(name)
  //          searchViewModel.searchAll.observe(activity!!, Observer<List<ResultSearchAll>>{
  //              searchList = it
  //              adapter!!.setData(searchList!!)
  //          })
  //
  //      }
  //
  //
  //
  //  }
  //








}
