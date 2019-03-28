package com.bunkalogic.bunkalist.Retrofit.Response.Movies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ProductionCountry {

    @SerializedName("iso_3166_1")
    @Expose
    var iso31661: String? = null
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
     * @param iso31661
     * @param name
     */
    constructor(iso31661: String, name: String) : super() {
        this.iso31661 = iso31661
        this.name = name
    }

}
