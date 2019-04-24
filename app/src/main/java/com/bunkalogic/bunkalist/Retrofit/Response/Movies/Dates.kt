package com.bunkalogic.bunkalist.Retrofit.Response.Movies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Dates {

    @SerializedName("maximum")
    @Expose
    var maximum: String? = null
    @SerializedName("minimum")
    @Expose
    var minimum: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param minimum
     * @param maximum
     */
    constructor(maximum: String, minimum: String) : super() {
        this.maximum = maximum
        this.minimum = minimum
    }

}