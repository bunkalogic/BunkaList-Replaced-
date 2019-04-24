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

    fun getMoviesPopular(callback: OnGetListMoviesCallback){
        searchRepository.getPopularMovies(callback)
    }

    fun getMoviesRated(callback: OnGetListMoviesCallback){
        searchRepository.getRatedMovies(callback)
    }

    fun getMoviesUpcoming(callback: OnGetListMoviesCallback){
        searchRepository.getUpcomingMovies(callback)
    }

    fun getSeriesPopular(callback: OnGetListSeriesCallback){
        searchRepository.getSeriesPopular(callback)
    }

    fun getSeriesRated(callback: OnGetListSeriesCallback){
        searchRepository.getSeriesRated(callback)
    }

    fun getSeriesUpcoming(callback: OnGetListSeriesCallback){
        searchRepository.getSeriesUpcoming(callback)
    }






}