package com.bunkalogic.bunkalist.Retrofit

import com.bunkalogic.bunkalist.Retrofit.Response.ResponseSearchMovies
import retrofit2.Call
import retrofit2.http.GET


interface MoviesOrSeriesAndAnimeService {

    @get:GET("/search/movie")
    val SearchMovies: Call<List<ResponseSearchMovies>>

}