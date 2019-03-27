package com.bunkalogic.bunkalist.data

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.bunkalogic.bunkalist.Retrofit.OnGetMoviesCallback

class ViewModelSearch(app: Application) : AndroidViewModel(app)  {

    private val searchRepository: RepositorySearch

    init {
        searchRepository = RepositorySearch()
    }

    fun getSearchAll(title: String, callback: OnGetMoviesCallback){
        searchRepository.getAll(title, callback)
    }




}