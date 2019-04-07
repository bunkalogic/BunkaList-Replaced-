package com.bunkalogic.bunkalist.Retrofit.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GenresResponse {

    @SerializedName("genres")
    @Expose
    var genres: List<Genre>? = null
}