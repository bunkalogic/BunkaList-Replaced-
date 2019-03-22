package com.bunkalogic.bunkalist.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.bunkalogic.bunkalist.Retrofit.MoviesOrSeriesAndAnimeClient
import com.bunkalogic.bunkalist.Retrofit.MoviesOrSeriesAndAnimeService
import com.bunkalogic.bunkalist.Retrofit.Response.ResponseSearchMovies
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositorySearch internal constructor() {
    internal var moviesOrSeriesAndAnimeClient: MoviesOrSeriesAndAnimeClient
    internal var moviesOrSeriesAndAnimeService: MoviesOrSeriesAndAnimeService
    internal var allSearchMovies: LiveData<List<ResultSearchMovies>>

    init {
        moviesOrSeriesAndAnimeClient = MoviesOrSeriesAndAnimeClient.getInstance()
        moviesOrSeriesAndAnimeService = moviesOrSeriesAndAnimeClient.getMoviesOrSeriesAndAnimeService()
        allSearchMovies = getAllMovies()
    }

    fun getAllMovies(): LiveData<List<ResultSearchMovies>>{
        val data = MutableLiveData<List<ResponseSearchMovies>>()

        val call = moviesOrSeriesAndAnimeService.SearchMovies

        call.enqueue(object : Callback<List<ResponseSearchMovies>>{
            override fun onFailure(call: Call<List<ResponseSearchMovies>>, t: Throwable) {
                Log.d("RepositorySearch", "Error connection")
            }

            override fun onResponse(
                call: Call<List<ResponseSearchMovies>>, response: Response<List<ResponseSearchMovies>>) {
                if (response.isSuccessful){
                    data.value = response.body()
                }else{
                    Log.d("RepositorySearch", "Something has gone wrong")
                }
            }

        })
        return getAllMovies()
    }

}






