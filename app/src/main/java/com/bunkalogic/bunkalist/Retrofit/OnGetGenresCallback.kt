package com.bunkalogic.bunkalist.Retrofit

import com.bunkalogic.bunkalist.Retrofit.Response.Genre


interface OnGetGenresCallback {

    fun onSuccess(genres: List<Genre>)

    fun onError()
}