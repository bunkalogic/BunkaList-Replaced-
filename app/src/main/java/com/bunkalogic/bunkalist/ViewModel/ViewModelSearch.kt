package com.bunkalogic.bunkalist.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.bunkalogic.bunkalist.Retrofit.*

class ViewModelSearch(app: Application) : AndroidViewModel(app)  {

    private val searchRepository: RepositorySearch

    init {
        searchRepository = RepositorySearch()
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






}