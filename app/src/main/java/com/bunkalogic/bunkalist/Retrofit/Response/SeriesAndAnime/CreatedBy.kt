package com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CreatedBy {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("credit_id")
    @Expose
    var creditId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("gender")
    @Expose
    var gender: Int? = null
    @SerializedName("profile_path")
    @Expose
    var profilePath: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param id
     * @param profilePath
     * @param name
     * @param gender
     * @param creditId
     */
    constructor(id: Int?, creditId: String, name: String, gender: Int?, profilePath: String) : super() {
        this.id = id
        this.creditId = creditId
        this.name = name
        this.gender = gender
        this.profilePath = profilePath
    }

}