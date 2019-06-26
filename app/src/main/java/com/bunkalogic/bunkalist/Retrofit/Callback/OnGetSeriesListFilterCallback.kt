package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResultSeries

interface OnGetSeriesListFilterCallback {

    fun onSuccess(page: Int, series: List<ResultSeries>)

    fun onError()
}