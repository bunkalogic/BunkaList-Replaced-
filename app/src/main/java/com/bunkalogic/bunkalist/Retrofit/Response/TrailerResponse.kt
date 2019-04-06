package com.bunkalogic.bunkalist.Retrofit.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class TrailerResponse {

    @SerializedName("results")
    @Expose
    var trailers: List<Trailer>? = null
}
