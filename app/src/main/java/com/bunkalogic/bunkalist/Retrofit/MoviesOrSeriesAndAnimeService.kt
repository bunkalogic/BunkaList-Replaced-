package com.bunkalogic.bunkalist.Retrofit

import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetMovieListFilterCallback
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetSeriesListFilterCallback
import com.bunkalogic.bunkalist.Retrofit.Response.GenresResponse
import com.bunkalogic.bunkalist.Retrofit.Response.ResponseSearchAll
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.Movie
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.MoviesResponse
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResponseUpcoming
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import com.bunkalogic.bunkalist.Retrofit.Response.People.*
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResponseSeries
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.Series
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path
import com.bunkalogic.bunkalist.Retrofit.Response.TrailerResponse






interface MoviesOrSeriesAndAnimeService {

    // https://api.themoviedb.org/3/search/multi?api_key=7bcf40aff5d7be80e294d763234a6930&language=en-US&query=cowboy%20bebop&page=1&include_adult=false

    @GET("search/multi")
    fun getSearchAll(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query")query: String
        //@Query("page") page: Int,
        //@Query("include_adult")include_adult: Boolean
    ): Call<ResponseSearchAll>


    @GET("movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<Movie>


    @GET("tv/{tv_id}")
    fun getSeries(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<Series>

    @GET("movie/{movie_id}/videos")
    fun getTrailersMovies(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
        //@Query("language") language: String
    ): Call<TrailerResponse>

    @GET("tv/{tv_id}/videos")
    fun getTrailersSeries(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String
        //@Query("language") language: String
    ): Call<TrailerResponse>

    @GET("genre/movie/list")
    fun getGenresMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<GenresResponse>

    @GET("genre/tv/list")
    fun getGenresTV(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<GenresResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("tv/popular")
    fun getPopularSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<ResponseSeries>

    @GET("tv/top_rated")
    fun getRatedSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<ResponseSeries>

    @GET("tv/on_the_air")
    fun getUpcomingSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<ResponseSeries>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendationsMovies(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("tv/{tv_id}/recommendations")
    fun getRecommendationsSeries(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<ResponseSeries>

    @GET("movie/{movie_id}/credits")
    fun getCreditsMovies(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<ResponsePeople>

    @GET("tv/{tv_id}/credits")
    fun getCreditsSeries(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<ResponsePeople>

    @GET("person/{person_id}")
        fun getPeopleData(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<ResultPeople>

    @GET("person/{person_id}/combined_credits")
    fun getPeopleDataCast(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<DataPeopleResponse>

    @GET("person/{person_id}/external_ids")
    fun getPeopleSocialMedia(
        @Path("person_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<PeopleSocialMediaResponse>


    @GET("discover/movie")
    fun getSearchFilterMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("page") page: Int,
        @Query("with_genres") with_genres: String,
        @Query("year") year: Int
    ): Call<MoviesResponse>

    @GET("discover/tv")
    fun getSearchFilterSeriesAndAnime(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("page") page: Int,
        @Query("first_air_date_year") first_air_date_year: Int,
        @Query("with_genres") with_genres: String
    ): Call<ResponseSeries>





}