package com.bunkalogic.bunkalist.Retrofit.Response.People

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ResultPeople {

    @SerializedName("birthday")
    @Expose
    var birthday: String? = null
    @SerializedName("known_for_department")
    @Expose
    var knownForDepartment: String? = null
    @SerializedName("deathday")
    @Expose
    var deathday: Any? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("also_known_as")
    @Expose
    var alsoKnownAs: List<String>? = null
    @SerializedName("gender")
    @Expose
    var gender: Int? = null
    @SerializedName("biography")
    @Expose
    var biography: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    @SerializedName("place_of_birth")
    @Expose
    var placeOfBirth: String? = null
    @SerializedName("profile_path")
    @Expose
    var profilePath: String? = null
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null
    @SerializedName("imdb_id")
    @Expose
    var imdbId: String? = null
    @SerializedName("homepage")
    @Expose
    var homepage: Any? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param birthday
     * @param profilePath
     * @param alsoKnownAs
     * @param placeOfBirth
     * @param adult
     * @param imdbId
     * @param biography
     * @param homepage
     * @param id
     * @param deathday
     * @param knownForDepartment
     * @param name
     * @param gender
     * @param popularity
     */
    constructor(
        birthday: String,
        knownForDepartment: String,
        deathday: Any,
        id: Int?,
        name: String,
        alsoKnownAs: List<String>,
        gender: Int?,
        biography: String,
        popularity: Double?,
        placeOfBirth: String,
        profilePath: String,
        adult: Boolean?,
        imdbId: String,
        homepage: Any
    ) : super() {
        this.birthday = birthday
        this.knownForDepartment = knownForDepartment
        this.deathday = deathday
        this.id = id
        this.name = name
        this.alsoKnownAs = alsoKnownAs
        this.gender = gender
        this.biography = biography
        this.popularity = popularity
        this.placeOfBirth = placeOfBirth
        this.profilePath = profilePath
        this.adult = adult
        this.imdbId = imdbId
        this.homepage = homepage
    }

}