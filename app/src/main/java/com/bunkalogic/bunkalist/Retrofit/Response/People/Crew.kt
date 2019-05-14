package com.bunkalogic.bunkalist.Retrofit.Response.People

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Crew {

    @SerializedName("credit_id")
    @Expose
    var creditId: String? = null
    @SerializedName("department")
    @Expose
    var department: String? = null
    @SerializedName("gender")
    @Expose
    var gender: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("job")
    @Expose
    var job: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
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
     * @param department
     * @param name
     * @param job
     * @param gender
     * @param creditId
     */
    constructor(
        creditId: String,
        department: String,
        gender: Int?,
        id: Int?,
        job: String,
        name: String,
        profilePath: String
    ) : super() {
        this.creditId = creditId
        this.department = department
        this.gender = gender
        this.id = id
        this.job = job
        this.name = name
        this.profilePath = profilePath
    }

}