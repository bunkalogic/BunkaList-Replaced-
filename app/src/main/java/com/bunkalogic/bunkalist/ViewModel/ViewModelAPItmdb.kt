package com.bunkalogic.bunkalist.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bunkalogic.bunkalist.Retrofit.Callback.*

class ViewModelAPItmdb(app: Application) : AndroidViewModel(app)  {

    private val searchRepository: RepositoryAPItmdb

    init {
        searchRepository = RepositoryAPItmdb()
    }

    fun getSearchAll(title: String, callback: OnGetSearchCallback){
        searchRepository.getAll(title, callback)
    }

    fun getMovie(Id: Int, callback: OnGetMovieCallback){
        searchRepository.getMovie(Id, callback)
    }

    fun getSeriesAndAnime(Id: Int, callback: OnGetSeriesCallback){
        searchRepository.getSeries(Id, callback)
    }

    fun getMovieRecommendations(Id: Int, callback: OnGetListMoviesCallback){
        searchRepository.getRecommendationsMovies(Id, callback)
    }

    fun getSeriesAndAnimeRecommendations(Id: Int, callback: OnGetListSeriesCallback){
        searchRepository.getRecommendationsSeries(Id, callback)
    }

    fun getTrailersMovies(Id: Int, callback: OnGetTrailersCallback){
        searchRepository.getTrailerMovie(Id, callback)
    }

    fun getTrailersSeries(Id: Int, callback: OnGetTrailersCallback){
        searchRepository.getTrailerSerieOrAnime(Id, callback)
    }

    fun getGenresMovies(callback: OnGetGenresCallback){
        searchRepository.getGenresMovies(callback)
    }

    fun getGenresSeries(callback: OnGetGenresCallback){
        searchRepository.getGenresSeries(callback)
    }

    fun getMoviesList(page: Int, sortBy: String, callback: OnGetListMoviesCallback){
        searchRepository.getPopularMovies(page, sortBy, callback)
    }


    fun getSeriesPopular(page: Int, callback: OnGetListSeriesCallback){
        searchRepository.getSeriesPopular(page, callback)
    }

    fun getSeriesRated(page: Int, callback: OnGetListSeriesCallback){
        searchRepository.getSeriesRated(page, callback)
    }

    fun getSeriesUpcoming(page: Int, callback: OnGetListSeriesCallback){
        searchRepository.getSeriesUpcoming(page, callback)
    }

    fun getPeopleMovies(Id: Int, callback: OnGetPeopleCallback){
        searchRepository.getPeopleMovies(Id, callback)
    }

    fun getPeopleSeries(Id: Int, callback: OnGetPeopleCallback){
        searchRepository.getPeopleSeries(Id, callback)
    }

    fun getPeopleData(Id: Int, callback: OnGetPeopleDataCallback){
        searchRepository.getPeopleData(Id, callback)
    }

    fun getPeopleDataCast(Id: Int, callback: OnGetPeopleDataCastCallback){
        searchRepository.getPeopleDataCast(Id, callback)
    }

    fun getPeopleSocialMedia(Id: Int, callback: OnGetPeopleSocialMediaCallback){
        searchRepository.getPeopleSocialMedia(Id, callback)
    }

    fun getMoviesFilters(callback: OnGetMovieListFilterCallback, sort_By: String, page: Int, withGenres: String, year: Int){
        searchRepository.getMoviesListFilter(callback, sort_By, page, withGenres, year)
    }

    fun getSeriesFilters(callback: OnGetSeriesListFilterCallback, sort_By: String, page: Int, withGenres: String, year: Int){
        searchRepository.getSeriesListFilter(callback, sort_By, page, withGenres, year)
    }










}