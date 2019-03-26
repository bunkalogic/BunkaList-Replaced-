package com.bunkalogic.bunkalist.data

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchAll

class ViewModelSearch(app: Application) : AndroidViewModel(app)  {

    private val searchRepository: RepositorySearch
    val searchAll: LiveData<List<ResultSearchAll>>

    private val LANGUAGE = "en-US"

    init {
        searchRepository = RepositorySearch()
        searchAll  = searchRepository.getAll()
    }


    fun getQuery(title: String){
        //searchRepository.moviesOrSeriesAndAnimeService.getSearchAll(Constans.API_KEY, title)
    }



}