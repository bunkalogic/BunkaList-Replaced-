package com.bunkalogic.bunkalist.Retrofit

import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.Retrofit.Response.AuthInterceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesOrSeriesAndAnimeClient {
    private val moviesOrSeriesAndAnimeService : MoviesOrSeriesAndAnimeService
    private val retrofit: Retrofit

    init {

        retrofit = Retrofit.Builder()
            .baseUrl(Constans.API_MOVIE_SERIES_ANIME_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        moviesOrSeriesAndAnimeService = retrofit.create(MoviesOrSeriesAndAnimeService::class.java)
    }

    companion object {
        private var instance: MoviesOrSeriesAndAnimeClient? = null

        // Pattern Singleton
        fun getInstance(): MoviesOrSeriesAndAnimeClient{
            if (instance == null){
                instance = MoviesOrSeriesAndAnimeClient()
            }
            return instance as MoviesOrSeriesAndAnimeClient
        }
    }


    fun getMoviesOrSeriesAndAnimeService(): MoviesOrSeriesAndAnimeService {

        return moviesOrSeriesAndAnimeService
    }
}