package com.bunkalogic.bunkalist.ViewModel

import android.util.Log
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.Retrofit.*
import com.bunkalogic.bunkalist.Retrofit.Callback.*
import com.bunkalogic.bunkalist.Retrofit.Response.*
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.Movie
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.MoviesResponse
import com.bunkalogic.bunkalist.Retrofit.Response.People.*
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.LogosNetworkResponse
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.Network
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResponseSeries
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.Series
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Year
import java.util.*



class RepositoryAPItmdb internal constructor() {
    internal var moviesOrSeriesAndAnimeClient: MoviesOrSeriesAndAnimeClient
    internal var moviesOrSeriesAndAnimeService: MoviesOrSeriesAndAnimeService


    init {
        moviesOrSeriesAndAnimeClient = MoviesOrSeriesAndAnimeClient.getInstance()
        moviesOrSeriesAndAnimeService = moviesOrSeriesAndAnimeClient.getMoviesOrSeriesAndAnimeService()

    }


    fun getGuestSession(callback : OnGetGuestSessionCallback){
        val call = moviesOrSeriesAndAnimeService.getGuestSession(Constans.API_KEY)

        call.enqueue(object: Callback<GuestSession>{
            override fun onFailure(call: Call<GuestSession>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection")
            }

            override fun onResponse(call: Call<GuestSession>, response: Response<GuestSession>) {
               if (response.isSuccessful){
                   val guestSessionResponse : GuestSession = response.body()!!
                   if (guestSessionResponse.success!! && preferences.userGuestSesionId!!.isEmpty()){
                       callback.onSuccess(guestSessionResponse.guestSessionId!!)
                   }else{
                       Log.d("RepositoryAPItmdb", "Guest Session: ${preferences.userGuestSesionId}")
                       Log.d("RepositoryAPItmdb", "Error cant create GuestSession")
                   }
               }else{
                   Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful GuestSession")
               }
            }

        })

    }






    fun getAll(title: String, callback: OnGetSearchCallback){
        val call = moviesOrSeriesAndAnimeService.getSearchAll(Constans.API_KEY,Locale.getDefault().language, title)

        call.enqueue(object : Callback<ResponseSearchAll>{

            override fun onResponse(call: Call<ResponseSearchAll>, response: Response<ResponseSearchAll>) {
                if (response.isSuccessful){
                    val searchResponse: ResponseSearchAll = response.body()!!
                    if (searchResponse.results != null){
                        callback.onSuccess(searchResponse.results!!)
                    }else {
                        Log.d("RepositoryAPItmdb", "Something has gone wrong")
                        callback.onError()
                    }
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful")
                }
            }
            override fun onFailure(call: Call<ResponseSearchAll>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection")
            }
        })

    }

    fun getMovie(Id: Int, callback: OnGetMovieCallback){
        val call = moviesOrSeriesAndAnimeService.getMovie(Id, Constans.API_KEY, Locale.getDefault().toString())

        call.enqueue(object : Callback<Movie>{
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Movies")
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful){
                    val movieResponse: Movie = response.body()!!

                    callback.onSuccess(movieResponse)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Movies")
                }
            }

        })
    }

    fun getSeries(Id: Int, callback: OnGetSeriesCallback){
        val call = moviesOrSeriesAndAnimeService.getSeries(Id, Constans.API_KEY, Locale.getDefault().toString())

        call.enqueue(object : Callback<Series>{
            override fun onFailure(call: Call<Series>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Series")
            }

            override fun onResponse(call: Call<Series>, response: Response<Series>) {
                if (response.isSuccessful){
                    val seriesResponse: Series = response.body()!!

                    if (seriesResponse != null){
                        callback.onSuccess(seriesResponse)
                    }else{
                        Log.d("RepositoryAPItmdb", "Something has gone wrong Series")
                        callback.onError()
                    }
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Series")
                }
            }

        })
    }


    fun getTrailerMovie(Id: Int, callback: OnGetTrailersCallback){
        val call = moviesOrSeriesAndAnimeService.getTrailersMovies(Id, Constans.API_KEY)

        call.enqueue(object : Callback<TrailerResponse>{
            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection TrailerMovie")
            }

            override fun onResponse(call: Call<TrailerResponse>, response: Response<TrailerResponse>) {
                if (response.isSuccessful){
                    val trailerResponse : TrailerResponse = response.body()!!

                    callback.onSuccess(trailerResponse.trailers!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Movies Trailers")
                }
            }

        })
    }



    fun getTrailerSerieOrAnime(Id: Int, callback: OnGetTrailersCallback){
        val call = moviesOrSeriesAndAnimeService.getTrailersSeries(Id, Constans.API_KEY)

        call.enqueue(object : Callback<TrailerResponse>{
            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Trailer Series")
            }

            override fun onResponse(call: Call<TrailerResponse>, response: Response<TrailerResponse>) {
                if (response.isSuccessful){
                    val trailerResponse : TrailerResponse = response.body()!!

                    callback.onSuccess(trailerResponse.trailers!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Series Trailers")
                }
            }

        })
    }


    fun getGenresMovies(callback: OnGetGenresCallback){
        val call = moviesOrSeriesAndAnimeService.getGenresMovie(Constans.API_KEY, Locale.getDefault().toString())

        call.enqueue(object : Callback<GenresResponse>{
            override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Genres")
            }

            override fun onResponse(call: Call<GenresResponse>, response: Response<GenresResponse>) {
                if (response.isSuccessful){
                    val genresResponse : GenresResponse = response.body()!!

                    callback.onSuccess(genresResponse.genres!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Genres Movies")
                }
            }

        })
    }


    fun getGenresSeries(callback: OnGetGenresCallback){
        val call = moviesOrSeriesAndAnimeService.getGenresTV(Constans.API_KEY, Locale.getDefault().toString())

        call.enqueue(object : Callback<GenresResponse>{
            override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Genres")
            }

            override fun onResponse(call: Call<GenresResponse>, response: Response<GenresResponse>) {
                if (response.isSuccessful){
                    val genresResponse : GenresResponse = response.body()!!

                    callback.onSuccess(genresResponse.genres!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Genres Movies")
                }
            }

        })
    }

    fun getTopsMovies(page: Int, sortBy: String, callback: OnGetListMoviesCallback){
        val call = object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                if (response.isSuccessful) {
                    Log.d("RepositoryAPItmdb", "Is Successful")
                    val moviesResponse = response.body()
                    if (moviesResponse != null) {
                        Log.d("RepositoryAPItmdb", "movieResponse is not null")
                        callback.onSuccess(moviesResponse.page!!, moviesResponse.results!!)
                    } else {
                        callback.onError()
                    }
                } else {
                    callback.onError()
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Movies List")
            }
        }

        when(sortBy){

            Constans.Popular_LIST -> moviesOrSeriesAndAnimeService.getPopularMovies(Constans.API_KEY, Locale.getDefault().language, page).enqueue(call)

            Constans.Rated_LIST -> moviesOrSeriesAndAnimeService.getTopRatedMovies(Constans.API_KEY, Locale.getDefault().language, page).enqueue(call)

            Constans.Upcoming_LIST -> moviesOrSeriesAndAnimeService.getUpcomingMovies(Constans.API_KEY, Locale.getDefault().language, page).enqueue(call)
        }
    }

    fun getTopsSeries(page: Int, sortBy: String, callback: OnGetListSeriesCallback){
        val call = object : Callback<ResponseSeries> {
            override fun onResponse(call: Call<ResponseSeries>, response: Response<ResponseSeries>) {
                if (response.isSuccessful) {
                    Log.d("RepositoryAPItmdb", "Is Successful")
                    val moviesResponse = response.body()
                    if (moviesResponse != null) {
                        Log.d("RepositoryAPItmdb", "movieResponse is not null")
                        callback.onSuccess(moviesResponse.page!!, moviesResponse.results!!)
                    } else {
                        callback.onError()
                    }
                } else {
                    callback.onError()
                }
            }

            override fun onFailure(call: Call<ResponseSeries>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Movies List")
            }
        }

        when(sortBy){

            Constans.Popular_LIST -> moviesOrSeriesAndAnimeService.getPopularSeries(Constans.API_KEY, Locale.getDefault().language, page).enqueue(call)

            Constans.Rated_LIST -> moviesOrSeriesAndAnimeService.getRatedSeries(Constans.API_KEY, Locale.getDefault().language, page).enqueue(call)

            Constans.Upcoming_LIST -> moviesOrSeriesAndAnimeService.getUpcomingSeries(Constans.API_KEY, Locale.getDefault().language, page).enqueue(call)
        }
    }

    fun getRecommendationsMovies(Id: Int, callback: OnGetListMoviesCallback){
        val call = moviesOrSeriesAndAnimeService.getRecommendationsMovies(Id, Constans.API_KEY, Locale.getDefault().toString(), 1)

        call.enqueue(object : Callback<MoviesResponse>{
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Movies Recommendations")
            }

            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                if (response.isSuccessful){
                    val recommendationsMoviesResponse : MoviesResponse = response.body()!!
                    callback.onSuccess(1, recommendationsMoviesResponse.results!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Movies Recommendations")
                }
            }

        })
    }

    fun getRecommendationsSeries(Id: Int, callback: OnGetListSeriesCallback){
        val call = moviesOrSeriesAndAnimeService.getRecommendationsSeries(Id, Constans.API_KEY, Locale.getDefault().toString(), 1)

        call.enqueue(object : Callback<ResponseSeries>{
            override fun onFailure(call: Call<ResponseSeries>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Movies Recommendations")
            }

            override fun onResponse(call: Call<ResponseSeries>, response: Response<ResponseSeries>) {
                if (response.isSuccessful){
                    val recommendationsSeriesResponse : ResponseSeries = response.body()!!
                    callback.onSuccess(1, recommendationsSeriesResponse.results!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Movies Recommendations")
                }
            }

        })
    }

    fun getPeopleMovies(Id : Int, callback: OnGetPeopleCallback){
        val call = moviesOrSeriesAndAnimeService.getCreditsMovies(Id, Constans.API_KEY)

        call.enqueue(object : Callback<ResponsePeople>{
            override fun onFailure(call: Call<ResponsePeople>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Movies Credits")
            }

            override fun onResponse(call: Call<ResponsePeople>, response: Response<ResponsePeople>) {
                if (response.isSuccessful){
                    val creditsMovie : ResponsePeople = response.body()!!
                    callback.onSuccess(creditsMovie.crew!!, creditsMovie.cast!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Movies Credits")
                }
            }

        })
    }

    fun getPeopleSeries(Id : Int, callback: OnGetPeopleCallback){
        val call = moviesOrSeriesAndAnimeService.getCreditsSeries(Id, Constans.API_KEY, Locale.getDefault().toString())

        call.enqueue(object : Callback<ResponsePeople>{
            override fun onFailure(call: Call<ResponsePeople>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Series Credits")
            }

            override fun onResponse(call: Call<ResponsePeople>, response: Response<ResponsePeople>) {
                if (response.isSuccessful){
                    val creditsSeries : ResponsePeople = response.body()!!
                    callback.onSuccess(creditsSeries.crew!!, creditsSeries.cast!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Series Credits")
                }
            }

        })
    }

    fun getPeopleData(Id : Int, callback: OnGetPeopleDataCallback){
        val call = moviesOrSeriesAndAnimeService.getPeopleData(Id, Constans.API_KEY, Locale.getDefault().language)

        call.enqueue(object : Callback<ResultPeople>{
            override fun onFailure(call: Call<ResultPeople>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection People Data")
            }

            override fun onResponse(call: Call<ResultPeople>, response: Response<ResultPeople>) {
                if (response.isSuccessful){
                    val peopleData : ResultPeople = response.body()!!
                    callback.onSuccess(peopleData)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in People Data")
                }
            }

        })
    }

    fun getPeopleDataCast(Id : Int, callback: OnGetPeopleDataCastCallback){
        val call = moviesOrSeriesAndAnimeService.getPeopleDataCast(Id, Constans.API_KEY, Locale.getDefault().toString())

        call.enqueue(object : Callback<DataPeopleResponse>{
            override fun onFailure(call: Call<DataPeopleResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection People Data Cast")
            }

            override fun onResponse(call: Call<DataPeopleResponse>, response: Response<DataPeopleResponse>) {
                if (response.isSuccessful){
                    val peopleData : DataPeopleResponse = response.body()!!
                    callback.onSuccess(peopleData.cast!!, peopleData.crew!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in People Data Cast")
                }
            }

        })
    }

    fun getPeopleSocialMedia(Id : Int, callback: OnGetPeopleSocialMediaCallback){
        val call = moviesOrSeriesAndAnimeService.getPeopleSocialMedia(Id, Constans.API_KEY, Locale.getDefault().toString())

        call.enqueue(object : Callback<PeopleSocialMediaResponse>{
            override fun onFailure(call: Call<PeopleSocialMediaResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection People Data")
            }

            override fun onResponse(call: Call<PeopleSocialMediaResponse>, response: Response<PeopleSocialMediaResponse>) {
                if (response.isSuccessful){
                    val peopleData : PeopleSocialMediaResponse = response.body()!!
                    callback.onSuccess(peopleData)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in People Data")
                }
            }

        })
    }

    fun getMoviesListFilter(callback: OnGetMovieListFilterCallback, sort_By: String, page: Int, withGenres: String, year: Int){
        val call = moviesOrSeriesAndAnimeService
            .getSearchFilterMovie(Constans.API_KEY, Locale.getDefault().language, sort_By, false, page, withGenres, year)

        call.enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection MoviesFilter")
            }

            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                if (response.isSuccessful){
                    val moviesFilter : MoviesResponse = response.body()!!
                    callback.onSuccess(moviesFilter.page!!, moviesFilter.results!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Movies Filter")
                }
            }

        })
    }

    fun getSeriesListFilter(callback: OnGetSeriesListFilterCallback, sort_By: String, page: Int, withGenres: String, year: Int){
        val call = moviesOrSeriesAndAnimeService
            .getSearchFilterSeriesAndAnime(Constans.API_KEY, Locale.getDefault().language, sort_By, false, page, year, withGenres)

        call.enqueue(object : Callback<ResponseSeries> {
            override fun onFailure(call: Call<ResponseSeries>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection MoviesFilter")
            }

            override fun onResponse(call: Call<ResponseSeries>, response: Response<ResponseSeries>) {
                if (response.isSuccessful){
                    val seriesFilter : ResponseSeries = response.body()!!
                    callback.onSuccess(seriesFilter.page!!,seriesFilter.results!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Movies Filter")
                }
            }

        })
    }


    fun getAnimeTops(callback: OnGetSeriesListFilterCallback, sort_By: String, page: Int, withGenres: String, year: Int, vote_count: Int){
        val call = moviesOrSeriesAndAnimeService
            .getFilterAnime(Constans.API_KEY, Locale.getDefault().language, sort_By, false, page, year, vote_count, withGenres, Locale.JAPANESE.language)

        call.enqueue(object : Callback<ResponseSeries> {
            override fun onFailure(call: Call<ResponseSeries>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection MoviesFilter")
            }

            override fun onResponse(call: Call<ResponseSeries>, response: Response<ResponseSeries>) {
                if (response.isSuccessful){
                    val seriesFilter : ResponseSeries = response.body()!!
                    callback.onSuccess(seriesFilter.page!!,seriesFilter.results!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Anime Tops Filter")
                }
            }

        })
    }


    fun getSeriesTops(callback: OnGetSeriesListFilterCallback, sort_By: String, page: Int, withoutGenres: String,  vote_count: Int){
        val call = moviesOrSeriesAndAnimeService
            .getTopsSeries(Constans.API_KEY, Locale.getDefault().language, sort_By, false, page, withoutGenres, vote_count)

        call.enqueue(object : Callback<ResponseSeries> {
            override fun onFailure(call: Call<ResponseSeries>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection MoviesFilter")
            }

            override fun onResponse(call: Call<ResponseSeries>, response: Response<ResponseSeries>) {
                if (response.isSuccessful){
                    val seriesFilter : ResponseSeries = response.body()!!
                    callback.onSuccess(seriesFilter.page!!,seriesFilter.results!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Movies Filter")
                }
            }

        })
    }

    fun postRateMovie(movieId: Int, valueRate: Double){
        val postRate = RatePost(valueRate)

        val call = moviesOrSeriesAndAnimeService
            .postRateMovie(movieId, Constans.API_KEY, preferences.userGuestSesionId!!, postRate)

        call.enqueue(object : Callback<RatePost>{
            override fun onFailure(call: Call<RatePost>, t: Throwable) {
                Log.d("RepositoryAPItmdb", "Error post Movie")
            }

            override fun onResponse(call: Call<RatePost>, response: Response<RatePost>) {
                if (response.isSuccessful){
                    Log.d("RepositoryAPItmdb", "Is postRateMovies successful: ${response.body().toString()}")
                }else{
                    Log.d("RepositoryAPItmdb", "Is postRateMovies not successful: ${response.body().toString()}")
                }
            }

        })
    }

    fun postRateSeriesAndAnime(serieId: Int, valueRate: Double){
        val postRate = RatePost(valueRate)

        val call = moviesOrSeriesAndAnimeService
            .postRateSeriesAndAnime(serieId, Constans.API_KEY, preferences.userGuestSesionId!!, postRate)

        call.enqueue(object : Callback<RatePost>{
            override fun onFailure(call: Call<RatePost>, t: Throwable) {
                Log.d("RepositoryAPItmdb", "Error post Serie and Anime")
            }

            override fun onResponse(call: Call<RatePost>, response: Response<RatePost>) {
                if (response.isSuccessful){
                    Log.d("RepositoryAPItmdb", "Is postRateSeries successful: ${response.body().toString()}")
                }else{
                    Log.d("RepositoryAPItmdb", "Is postRateSeries not successful: ${response.body().toString()}")
                }
            }

        })
    }


    fun getNetworkSeriesTv(Id : Int, callback: OnGetNetworkTVCallback){
        val call = moviesOrSeriesAndAnimeService.getNetworkTv(Id, Constans.API_KEY)

        call.enqueue(object : Callback<LogosNetworkResponse>{
            override fun onFailure(call: Call<LogosNetworkResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Network Tv")
            }

            override fun onResponse(call: Call<LogosNetworkResponse>, response: Response<LogosNetworkResponse>) {
                if (response.isSuccessful){
                    val netTV : LogosNetworkResponse = response.body()!!
                    callback.onSuccess(netTV.logos!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Network Tv")
                }
            }

        })
    }


    fun getSimilarMovies(Id: Int, callback: OnGetListMoviesCallback){
        val call = moviesOrSeriesAndAnimeService.getSimilarMovies(Id, Constans.API_KEY, Locale.getDefault().toString(), 1)

        call.enqueue(object : Callback<MoviesResponse>{
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Movies Similar")
            }

            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                if (response.isSuccessful){
                    val recommendationsMoviesResponse : MoviesResponse = response.body()!!
                    callback.onSuccess(1, recommendationsMoviesResponse.results!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Movies Similar")
                }
            }

        })
    }

    fun getSimilarSeries(Id: Int, callback: OnGetListSeriesCallback){
        val call = moviesOrSeriesAndAnimeService.getSimilarSeries(Id, Constans.API_KEY, Locale.getDefault().toString(), 1)

        call.enqueue(object : Callback<ResponseSeries>{
            override fun onFailure(call: Call<ResponseSeries>, t: Throwable) {
                callback.onError()
                Log.d("RepositoryAPItmdb", "Error connection Movies Recommendations")
            }

            override fun onResponse(call: Call<ResponseSeries>, response: Response<ResponseSeries>) {
                if (response.isSuccessful){
                    val recommendationsSeriesResponse : ResponseSeries = response.body()!!
                    callback.onSuccess(1, recommendationsSeriesResponse.results!!)
                }else{
                    Log.d("RepositoryAPItmdb", "Something has gone wrong on response.isSuccessful in Movies Recommendations")
                }
            }

        })
    }









}







