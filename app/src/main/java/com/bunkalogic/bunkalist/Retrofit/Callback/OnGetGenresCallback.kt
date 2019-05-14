package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.Genre


interface OnGetGenresCallback {

    fun onSuccess(genres: List<Genre>)

    fun onError()
}