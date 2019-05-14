package com.bunkalogic.bunkalist.Retrofit.Response.People

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CastResponse {

    @SerializedName("cast")
    @Expose
    var cast: List<CastResult>? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param id
     * @param cast
     */
    constructor(cast: List<CastResult>, id: Int?) : super() {
        this.cast = cast
        this.id = id
    }

}