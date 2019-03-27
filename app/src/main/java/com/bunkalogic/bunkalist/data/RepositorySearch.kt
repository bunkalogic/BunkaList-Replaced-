package com.bunkalogic.bunkalist.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.Retrofit.MoviesOrSeriesAndAnimeClient
import com.bunkalogic.bunkalist.Retrofit.MoviesOrSeriesAndAnimeService
import com.bunkalogic.bunkalist.Retrofit.OnGetMoviesCallback
import com.bunkalogic.bunkalist.Retrofit.Response.ResponseSearchAll
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositorySearch internal constructor() {
    internal var moviesOrSeriesAndAnimeClient: MoviesOrSeriesAndAnimeClient
    internal var moviesOrSeriesAndAnimeService: MoviesOrSeriesAndAnimeService


    init {
        moviesOrSeriesAndAnimeClient = MoviesOrSeriesAndAnimeClient.getInstance()
        moviesOrSeriesAndAnimeService = moviesOrSeriesAndAnimeClient.getMoviesOrSeriesAndAnimeService()

    }

    fun getAll(title: String, callback: OnGetMoviesCallback){
        val call = moviesOrSeriesAndAnimeService.getSearchAll(Constans.API_KEY, title)

        call.enqueue(object : Callback<ResponseSearchAll>{

            override fun onResponse(call: Call<ResponseSearchAll>, response: Response<ResponseSearchAll>) {
                if (response.isSuccessful){
                    val searchResponse: ResponseSearchAll = response.body()!!
                    if (searchResponse.results != null){
                        callback.onSuccess(searchResponse.results!!)
                    }else {
                        Log.d("RepositorySearch", "Something has gone wrong")
                        callback.onError()
                    }
                }else{
                    Log.d("RepositorySearch", "Something has gone wrong on response.isSuccessful")
                }
            }
            override fun onFailure(call: Call<ResponseSearchAll>, t: Throwable) {
                callback.onError()
                Log.d("FragmentSearch", "Error connection")
            }
        })

    }

}







