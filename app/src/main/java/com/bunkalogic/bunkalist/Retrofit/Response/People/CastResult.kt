package com.bunkalogic.bunkalist.Retrofit.Response.People

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CastResult {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null
    @SerializedName("episode_count")
    @Expose
    var episodeCount: Int? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("origin_country")
    @Expose
    var originCountry: List<String>? = null
    @SerializedName("original_name")
    @Expose
    var originalName: String? = null
    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("media_type")
    @Expose
    var mediaType: String? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    @SerializedName("first_air_date")
    @Expose
    var firstAirDate: String? = null
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null
    @SerializedName("character")
    @Expose
    var character: String? = null
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    @SerializedName("credit_id")
    @Expose
    var creditId: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param genreIds
     * @param episodeCount
     * @param originalName
     * @param originalLanguage
     * @param creditId
     * @param backdropPath
     * @param voteCount
     * @param mediaType
     * @param character
     * @param id
     * @param originCountry
     * @param overview
     * @param name
     * @param posterPath
     * @param firstAirDate
     * @param voteAverage
     * @param popularity
     */
    constructor(
        id: Int?,
        originalLanguage: String,
        episodeCount: Int?,
        overview: String,
        originCountry: List<String>,
        originalName: String,
        genreIds: List<Int>,
        name: String,
        mediaType: String,
        posterPath: String,
        firstAirDate: String,
        voteAverage: Double?,
        voteCount: Int?,
        character: String,
        backdropPath: String,
        popularity: Double?,
        creditId: String,
        title: String
    ) : super() {
        this.id = id
        this.originalLanguage = originalLanguage
        this.episodeCount = episodeCount
        this.overview = overview
        this.originCountry = originCountry
        this.originalName = originalName
        this.genreIds = genreIds
        this.name = name
        this.mediaType = mediaType
        this.posterPath = posterPath
        this.firstAirDate = firstAirDate
        this.voteAverage = voteAverage
        this.voteCount = voteCount
        this.character = character
        this.backdropPath = backdropPath
        this.popularity = popularity
        this.creditId = creditId
        this.title = title
    }

}