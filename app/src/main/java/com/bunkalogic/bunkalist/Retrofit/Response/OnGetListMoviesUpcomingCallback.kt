package com.bunkalogic.bunkalist.Retrofit.Response

import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie

interface OnGetListMoviesUpcomingCallback {

    fun onSuccess(page : Int, movies: List<ResultMovie>)

    fun onError()
}