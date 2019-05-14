package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResultSeries

interface OnGetListSeriesCallback {

    fun onSuccess(page: Int, series: List<ResultSeries>)

    fun onError()
}