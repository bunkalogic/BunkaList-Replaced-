package com.bunkalogic.bunkalist.Retrofit.Response.Movies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ProductionCompany {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("logo_path")
    @Expose
    var logoPath: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
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
    constructor(id: Int?, logoPath: String, name: String, originCountry: String) : super() {
        this.id = id
        this.logoPath = logoPath
        this.name = name
        this.originCountry = originCountry
    }

}