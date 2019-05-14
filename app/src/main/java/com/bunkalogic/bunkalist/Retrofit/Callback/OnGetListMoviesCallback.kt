package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie

interface OnGetListMoviesCallback {

    fun onSuccess(page : Int, movies: List<ResultMovie>)

    fun onError()
}