package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie

interface OnGetMovieListFilterCallback {

    fun onSuccess(page : Int, movies: List<ResultMovie>)

    fun onError()
}