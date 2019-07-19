package com.bunkalogic.bunkalist.Retrofit.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ResultSearchAll {

    @SerializedName("original_name")
    @Expose
    var originalName: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("media_type")
    @Expose
    var mediaType: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    @SerializedName("first_air_date")
    @Expose
    var firstAirDate: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: Any? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("origin_country")
    @Expose
    var originCountry: List<String>? = null
    @SerializedName("video")
    @Expose
    var video: Boolean? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null
    @SerializedName("release_date")
    @Expose
    var profilePath: String? = null
    @SerializedName("profile_path")
    @Expose
    var releaseDate: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param genreIds
     * @param originalName
     * @param originalLanguage
     * @param adult
     * @param backdropPath
     * @param voteCount
     * @param mediaType
     * @param id
     * @param title
     * @param releaseDate
     * @param originCountry
     * @param overview
     * @param name
     * @param posterPath
     * @param originalTitle
     * @param firstAirDate
     * @param voteAverage
     * @param video
     * @param popularity
     */
    constructor(
        originalName: String,
        id: Int?,
        mediaType: String,
        name: String,
        voteCount: Int?,
        voteAverage: Double?,
        posterPath: String,
        firstAirDate: String,
        popularity: Double?,
        genreIds: List<Int>,
        originalLanguage: String,
        backdropPath: Any,
        overview: String,
        originCountry: List<String>,
        video: Boolean?,
        title: String,
        originalTitle: String,
        adult: Boolean?,
        releaseDate: String,
        profilePath: String
    ) : super() {
        this.originalName = originalName
        this.id = id
        this.mediaType = mediaType
        this.name = name
        this.voteCount = voteCount
        this.voteAverage = voteAverage
        this.posterPath = posterPath
        this.firstAirDate = firstAirDate
        this.popularity = popularity
        this.genreIds = genreIds
        this.originalLanguage = originalLanguage
        this.backdropPath = backdropPath
        this.overview = overview
        this.originCountry = originCountry
        this.video = video
        this.title = title
        this.originalTitle = originalTitle
        this.adult = adult
        this.releaseDate = releaseDate
        this.profilePath = profilePath
    }

}
