package com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Network {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("logo_path")
    @Expose
    var logoPath: String? = null
    @SerializedName("origin_country")
    @Expose
    var originCountry: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param id
     * @param originCountry
     * @param name
     * @param logoPath
     */
    constructor(name: String, id: Int?, logoPath: String, originCountry: String) : super() {
        this.name = name
        this.id = id
        this.logoPath = logoPath
        this.originCountry = originCountry
    }

}