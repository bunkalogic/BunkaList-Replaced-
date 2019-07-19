package com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class LogosNetworkResponse {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("logos")
    @Expose
    var logos: List<LogosNetwork>? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param logos
     * @param id
     */
    constructor(id: Int?, logos: List<LogosNetwork>) : super() {
        this.id = id
        this.logos = logos
    }

}