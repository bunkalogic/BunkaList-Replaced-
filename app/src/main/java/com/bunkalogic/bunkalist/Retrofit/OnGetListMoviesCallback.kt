package com.bunkalogic.bunkalist.Retrofit

import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie

interface OnGetListMoviesCallback {

    fun onSuccess(movies: List<ResultMovie>)

    fun onError()
}