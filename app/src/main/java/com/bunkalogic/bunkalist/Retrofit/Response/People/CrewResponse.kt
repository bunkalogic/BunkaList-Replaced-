package com.bunkalogic.bunkalist.Retrofit.Response.People

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CrewResponse {

    @SerializedName("cast")
    @Expose
    var cast: List<Any>? = null
    @SerializedName("crew")
    @Expose
    var crew: List<CrewResult>? = null
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
     * @param crew
     */
    constructor(cast: List<Any>, crew: List<CrewResult>, id: Int?) : super() {
        this.cast = cast
        this.crew = crew
        this.id = id
    }

}