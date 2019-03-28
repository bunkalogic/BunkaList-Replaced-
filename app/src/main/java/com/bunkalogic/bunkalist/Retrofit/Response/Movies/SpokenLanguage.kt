package com.bunkalogic.bunkalist.Retrofit.Response.Movies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class SpokenLanguage {

    @SerializedName("iso_639_1")
    @Expose
    var iso6391: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param iso6391
     * @param name
     */
    constructor(iso6391: String, name: String) : super() {
        this.iso6391 = iso6391
        this.name = name
    }

}