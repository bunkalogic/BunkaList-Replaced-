package com.bunkalogic.bunkalist.Retrofit.Response.People

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ResponsePeople {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("cast")
    @Expose
    var cast: List<Cast>? = null
    @SerializedName("crew")
    @Expose
    var crew: List<Crew>? = null

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
    constructor(id: Int?, cast: List<Cast>, crew: List<Crew>) : super() {
        this.id = id
        this.cast = cast
        this.crew = crew
    }

}