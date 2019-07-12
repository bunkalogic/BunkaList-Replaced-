package com.bunkalogic.bunkalist.Retrofit

import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetMovieListFilterCallback
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetSeriesListFilterCallback
import com.bunkalogic.bunkalist.Retrofit.Response.*
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.Movie
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.MoviesResponse
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResponseUpcoming
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import com.bunkalogic.bunkalist.Retrofit.Response.People.*
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResponseSeries
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.Series
import retrofit2.Call
import retrofit2.http.*


interface MoviesOrSeriesAndAnimeService {

    // https://api.themoviedb.org/3/search/multi?api_key=7bcf40aff5d7be80e294d763234a6930&language=en-US&query=cowboy%20bebop&page=1&include_adult=false

    @GET("authentication/guest_session/new")
    fun getGuestSession(
        @Query("api_key") apiKey: String
    ): Call<GuestSession>

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

    @GET("discover/tv")
    fun getFilterAnime(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("page") page: Int,
        @Query("first_air_date_year") first_air_date_year: Int,
        @Query("vote_count.gte") vote_count: Int,
        @Query("with_genres") with_genres: String,
        @Query("with_original_language") with_original_language: String
    ): Call<ResponseSeries>


    @GET("discover/tv")
    fun getTopsSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("page") page: Int,
        @Query("without_genres") without_genres: String,
        @Query("vote_count.gte") vote_count: Int
    ): Call<ResponseSeries>


    @POST("movie/{movie_id}/rating")
    fun postRateMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("guest_session_id") guest_session_id: String,
        @Body ratePost : RatePost
    ): Call<RatePost>

    @POST("tv/{tv_id}/rating")
    fun postRateSeriesAndAnime(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") apiKey: String,
        @Query("guest_session_id") guest_session_id: String,
        @Body ratePost : RatePost
    ): Call<RatePost>





}