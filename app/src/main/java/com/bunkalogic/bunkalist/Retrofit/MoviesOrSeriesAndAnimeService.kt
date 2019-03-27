package com.bunkalogic.bunkalist.Retrofit

import com.bunkalogic.bunkalist.Retrofit.Response.ResponseSearchAll
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesOrSeriesAndAnimeService {

    // https://api.themoviedb.org/3/search/multi?api_key=7bcf40aff5d7be80e294d763234a6930&language=en-US&query=cowboy%20bebop&page=1&include_adult=false

    @GET("search/multi")
    fun getSearchAll(
        @Query("api_key") apiKey: String,
        //@Query("language") language: String,
        @Query("query")query: String
        //@Query("page") page: Int,
        //@Query("include_adult")include_adult: Boolean
    ): Call<ResponseSearchAll>
}