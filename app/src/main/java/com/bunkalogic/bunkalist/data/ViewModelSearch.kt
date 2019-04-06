package com.bunkalogic.bunkalist.data

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.bunkalogic.bunkalist.Retrofit.OnGetMovieCallback
import com.bunkalogic.bunkalist.Retrofit.OnGetSearchCallback
import com.bunkalogic.bunkalist.Retrofit.OnGetSeriesCallback
import com.bunkalogic.bunkalist.Retrofit.OnGetTrailersCallback

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




}