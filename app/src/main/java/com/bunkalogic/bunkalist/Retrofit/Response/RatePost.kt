package com.bunkalogic.bunkalist.Retrofit.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class RatePost {

    @SerializedName("value")
    @Expose
    var value: Double? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param value
     */
    constructor(value: Double?) : super() {
        this.value = value
    }

}