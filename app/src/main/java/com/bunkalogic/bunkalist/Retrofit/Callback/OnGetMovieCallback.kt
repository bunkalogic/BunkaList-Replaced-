package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.Movies.Movie

interface OnGetMovieCallback {

    fun onSuccess(movie: Movie)

    fun onError()
}