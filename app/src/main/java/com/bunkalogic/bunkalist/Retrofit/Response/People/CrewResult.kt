package com.bunkalogic.bunkalist.Retrofit.Response.People

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CrewResult {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("department")
    @Expose
    var department: String? = null
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null
    @SerializedName("episode_count")
    @Expose
    var episodeCount: Int? = null
    @SerializedName("job")
    @Expose
    var job: String? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null
    @SerializedName("video")
    @Expose
    var video: Boolean? = null
    @SerializedName("media_type")
    @Expose
    var mediaType: String? = null
    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    @SerializedName("credit_id")
    @Expose
    var creditId: String? = null
    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null
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
     * @param genreIds
     * @param department
     * @param job
     * @param originalLanguage
     * @param adult
     * @param creditId
     * @param backdropPath
     * @param voteCount
     * @param mediaType
     * @param id
     * @param title
     * @param releaseDate
     * @param overview
     * @param posterPath
     * @param originalTitle
     * @param voteAverage
     * @param video
     * @param popularity
     * @param name
     * @param episodeCount
     */
    constructor(
        id: Int?,
        department: String,
        originalLanguage: String,
        originalTitle: String,
        job: String,
        overview: String,
        voteCount: Int?,
        video: Boolean?,
        mediaType: String,
        releaseDate: String,
        voteAverage: Double?,
        title: String,
        popularity: Double?,
        genreIds: List<Int>,
        backdropPath: String,
        adult: Boolean?,
        posterPath: String,
        creditId: String,
        name: String,
        episodeCount: Int?
    ) : super() {
        this.id = id
        this.department = department
        this.originalLanguage = originalLanguage
        this.originalTitle = originalTitle
        this.job = job
        this.overview = overview
        this.voteCount = voteCount
        this.video = video
        this.mediaType = mediaType
        this.releaseDate = releaseDate
        this.voteAverage = voteAverage
        this.title = title
        this.popularity = popularity
        this.genreIds = genreIds
        this.backdropPath = backdropPath
        this.adult = adult
        this.posterPath = posterPath
        this.creditId = creditId
        this.name = name
        this.episodeCount = episodeCount
    }

}