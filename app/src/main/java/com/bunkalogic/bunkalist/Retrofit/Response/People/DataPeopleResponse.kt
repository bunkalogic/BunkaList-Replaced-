package com.bunkalogic.bunkalist.Retrofit.Response.People

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DataPeopleResponse {

    @SerializedName("cast")
    @Expose
    var cast: List<CastResult>? = null
    @SerializedName("crew")
    @Expose
    var crew: List<CrewResult>? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param cast
     * @param crew
     */
    constructor(cast: List<CastResult>, crew: List<CrewResult>) : super() {
        this.cast = cast
        this.crew = crew
    }

}