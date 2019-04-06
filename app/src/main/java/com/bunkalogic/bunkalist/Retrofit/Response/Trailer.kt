package com.bunkalogic.bunkalist.Retrofit.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Trailer {

    @SerializedName("key")
    @Expose
    var key: String? = null
}