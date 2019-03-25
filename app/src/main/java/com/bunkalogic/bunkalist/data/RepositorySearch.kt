package com.bunkalogic.bunkalist.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.Retrofit.MoviesOrSeriesAndAnimeClient
import com.bunkalogic.bunkalist.Retrofit.MoviesOrSeriesAndAnimeService
import com.bunkalogic.bunkalist.Retrofit.Response.ResponseSearchAll
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositorySearch internal constructor() {
    internal var moviesOrSeriesAndAnimeClient: MoviesOrSeriesAndAnimeClient
    internal var moviesOrSeriesAndAnimeService: MoviesOrSeriesAndAnimeService
    internal var searchAll: LiveData<List<ResultSearchAll>>
    private val LANGUAGE = "en-US"


    init {
        moviesOrSeriesAndAnimeClient = MoviesOrSeriesAndAnimeClient.getInstance()
        moviesOrSeriesAndAnimeService = moviesOrSeriesAndAnimeClient.getMoviesOrSeriesAndAnimeService()
        searchAll = getAll()
    }

    fun getAll(): LiveData<List<ResultSearchAll>>{
        val data = MutableLiveData<ResponseSearchAll>()


        val call = moviesOrSeriesAndAnimeService.getSearchAll(Constans.API_KEY, LANGUAGE, "")

        call.enqueue(object : Callback<ResponseSearchAll>{
            override fun onFailure(call: Call<ResponseSearchAll>, t: Throwable) {
                Log.d("RepositorySearch", "Error connection")
            }

            override fun onResponse(call: Call<ResponseSearchAll>, response: Response<ResponseSearchAll>) {
                if (response.isSuccessful){
                    data.value = response.body()

                }else{
                    Log.d("RepositorySearch", "Something has gone wrong")
                }

            }

        })

        return getAll()
    }

}







