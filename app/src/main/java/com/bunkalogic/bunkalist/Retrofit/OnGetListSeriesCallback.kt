package com.bunkalogic.bunkalist.Retrofit

import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResultSeries

interface OnGetListSeriesCallback {

    fun onSuccess(movies: List<ResultSeries>)

    fun onError()
}