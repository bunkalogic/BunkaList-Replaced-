package com.bunkalogic.bunkalist.ViewModel

import android.util.Log
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.Retrofit.*
import com.bunkalogic.bunkalist.Retrofit.Response.GenresResponse
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.Movie
import com.bunkalogic.bunkalist.Retrofit.Response.ResponseSearchAll
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.Series
import com.bunkalogic.bunkalist.Retrofit.Response.TrailerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RepositorySearch internal constructor() {
    internal var moviesOrSeriesAndAnimeClient: MoviesOrSeriesAndAnimeClient
    internal var moviesOrSeriesAndAnimeService: MoviesOrSeriesAndAnimeService


    init {
        moviesOrSeriesAndAnimeClient = MoviesOrSeriesAndAnimeClient.getInstance()
        moviesOrSeriesAndAnimeService = moviesOrSeriesAndAnimeClient.getMoviesOrSeriesAndAnimeService()

    }

    fun getAll(title: String, callback: OnGetSearchCallback){
        val call = moviesOrSeriesAndAnimeService.getSearchAll(Constans.API_KEY,Locale.getDefault().toString(), title)

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
                Log.d("RepositorySearch", "Error connection")
            }
        })

    }

    fun getMovie(Id: Int, callback: OnGetMovieCallback){
        val call = moviesOrSeriesAndAnimeService.getMovie(Id, Constans.API_KEY, Locale.getDefault().toString())

        call.enqueue(object : Callback<Movie>{
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                callback.onError()
                Log.d("RepositorySearch", "Error connection Movies")
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful){
                    val movieResponse: Movie = response.body()!!

                    callback.onSuccess(movieResponse)
                }else{
                    Log.d("RepositorySearch", "Something has gone wrong on response.isSuccessful in Movies")
                }
            }

        })
    }

    fun getSeries(Id: Int, callback: OnGetSeriesCallback){
        val call = moviesOrSeriesAndAnimeService.getSeries(Id, Constans.API_KEY, Locale.getDefault().toString())

        call.enqueue(object : Callback<Series>{
            override fun onFailure(call: Call<Series>, t: Throwable) {
                callback.onError()
                Log.d("RepositorySearch", "Error connection Series")
            }

            override fun onResponse(call: Call<Series>, response: Response<Series>) {
                if (response.isSuccessful){
                    val seriesResponse: Series = response.body()!!

                    if (seriesResponse != null){
                        callback.onSuccess(seriesResponse)
                    }else{
                        Log.d("RepositorySearch", "Something has gone wrong Series")
                        callback.onError()
                    }
                }else{
                    Log.d("RepositorySearch", "Something has gone wrong on response.isSuccessful in Series")
                }
            }

        })
    }


    fun getTrailerMovie(Id: Int, callback: OnGetTrailersCallback){
        val call = moviesOrSeriesAndAnimeService.getTrailersMovies(Id, Constans.API_KEY)

        call.enqueue(object : Callback<TrailerResponse>{
            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositorySearch", "Error connection TrailerMovie")
            }

            override fun onResponse(call: Call<TrailerResponse>, response: Response<TrailerResponse>) {
                if (response.isSuccessful){
                    val trailerResponse : TrailerResponse = response.body()!!

                    callback.onSuccess(trailerResponse.trailers!!)
                }else{
                    Log.d("RepositorySearch", "Something has gone wrong on response.isSuccessful in Movies Trailers")
                }
            }

        })
    }



    fun getTrailerSerieOrAnime(Id: Int, callback: OnGetTrailersCallback){
        val call = moviesOrSeriesAndAnimeService.getTrailersSeries(Id, Constans.API_KEY)

        call.enqueue(object : Callback<TrailerResponse>{
            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositorySearch", "Error connection Trailer Series")
            }

            override fun onResponse(call: Call<TrailerResponse>, response: Response<TrailerResponse>) {
                if (response.isSuccessful){
                    val trailerResponse : TrailerResponse = response.body()!!

                    callback.onSuccess(trailerResponse.trailers!!)
                }else{
                    Log.d("RepositorySearch", "Something has gone wrong on response.isSuccessful in Series Trailers")
                }
            }

        })
    }


    fun getGenresMovies(callback: OnGetGenresCallback){
        val call = moviesOrSeriesAndAnimeService.getGenresMovie(Constans.API_KEY, Locale.getDefault().toString())

        call.enqueue(object : Callback<GenresResponse>{
            override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositorySearch", "Error connection Genres")
            }

            override fun onResponse(call: Call<GenresResponse>, response: Response<GenresResponse>) {
                if (response.isSuccessful){
                    val genresResponse : GenresResponse = response.body()!!

                    callback.onSuccess(genresResponse.genres!!)
                }else{
                    Log.d("RepositorySearch", "Something has gone wrong on response.isSuccessful in Genres Movies")
                }
            }

        })
    }


    fun getGenresSeries(callback: OnGetGenresCallback){
        val call = moviesOrSeriesAndAnimeService.getGenresTV(Constans.API_KEY, Locale.getDefault().toString())

        call.enqueue(object : Callback<GenresResponse>{
            override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositorySearch", "Error connection Genres")
            }

            override fun onResponse(call: Call<GenresResponse>, response: Response<GenresResponse>) {
                if (response.isSuccessful){
                    val genresResponse : GenresResponse = response.body()!!

                    callback.onSuccess(genresResponse.genres!!)
                }else{
                    Log.d("RepositorySearch", "Something has gone wrong on response.isSuccessful in Genres Movies")
                }
            }

        })
    }

}







