package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.LogosNetwork

interface OnGetNetworkTVCallback {

        fun onSuccess(network: List<LogosNetwork>)

        fun onError()

}