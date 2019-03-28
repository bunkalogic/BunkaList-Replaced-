package com.bunkalogic.bunkalist.Retrofit.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Genre {

    @SerializedName("id")
    @Expose
    var id: Int? = null
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
     * @param id
     * @param name
     */
    constructor(id: Int?, name: String) : super() {
        this.id = id
        this.name = name
    }

}