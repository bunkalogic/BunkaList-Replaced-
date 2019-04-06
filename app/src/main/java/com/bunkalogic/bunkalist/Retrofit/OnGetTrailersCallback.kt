package com.bunkalogic.bunkalist.Retrofit

import com.bunkalogic.bunkalist.Retrofit.Response.Trailer


interface OnGetTrailersCallback {

    fun onSuccess(trailers: List<Trailer>)

    fun onError()
}