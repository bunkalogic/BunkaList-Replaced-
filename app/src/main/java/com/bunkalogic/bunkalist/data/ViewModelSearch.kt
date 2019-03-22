package com.bunkalogic.bunkalist.data

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchMovies

class ViewModelSearch(app: Application) : AndroidViewModel(app)  {

    private val searchRepository: RepositorySearch
    val searchMovies: LiveData<List<ResultSearchMovies>>

    init {
        searchRepository = RepositorySearch()
        searchMovies  = searchRepository.getAllMovies()
    }

}