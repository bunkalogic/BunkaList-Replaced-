package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.Trailer


interface OnGetTrailersCallback {

    fun onSuccess(trailers: List<Trailer>)

    fun onError()
}