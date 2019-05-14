package com.bunkalogic.bunkalist.Retrofit.Response.People

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Cast {

    @SerializedName("cast_id")
    @Expose
    var castId: Int? = null
    @SerializedName("character")
    @Expose
    var character: String? = null
    @SerializedName("credit_id")
    @Expose
    var creditId: String? = null
    @SerializedName("gender")
    @Expose
    var gender: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("order")
    @Expose
    var order: Int? = null
    @SerializedName("profile_path")
    @Expose
    var profilePath: Any? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param id
     * @param profilePath
     * @param order
     * @param castId
     * @param name
     * @param gender
     * @param creditId
     * @param character
     */
    constructor(
        castId: Int?,
        character: String,
        creditId: String,
        gender: Int?,
        id: Int?,
        name: String,
        order: Int?,
        profilePath: Any
    ) : super() {
        this.castId = castId
        this.character = character
        this.creditId = creditId
        this.gender = gender
        this.id = id
        this.name = name
        this.order = order
        this.profilePath = profilePath
    }

}