package com.bunkalogic.bunkalist.Retrofit

import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie

interface OnGetListMoviesCallback {

    fun onSuccess(page : Int, movies: List<ResultMovie>)

    fun onError()
}