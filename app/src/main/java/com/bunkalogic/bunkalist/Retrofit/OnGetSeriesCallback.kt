package com.bunkalogic.bunkalist.Retrofit

import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.Series

interface OnGetSeriesCallback {

    fun onSuccess(series: Series)

    fun onError()
}