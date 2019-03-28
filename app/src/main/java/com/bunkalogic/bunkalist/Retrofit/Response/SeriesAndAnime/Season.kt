package com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Season {

    @SerializedName("air_date")
    @Expose
    var airDate: String? = null
    @SerializedName("episode_count")
    @Expose
    var episodeCount: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    @SerializedName("season_number")
    @Expose
    var seasonNumber: Int? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param id
     * @param airDate
     * @param overview
     * @param posterPath
     * @param name
     * @param episodeCount
     * @param seasonNumber
     */
    constructor(
        airDate: String,
        episodeCount: Int?,
        id: Int?,
        name: String,
        overview: String,
        posterPath: String,
        seasonNumber: Int?
    ) : super() {
        this.airDate = airDate
        this.episodeCount = episodeCount
        this.id = id
        this.name = name
        this.overview = overview
        this.posterPath = posterPath
        this.seasonNumber = seasonNumber
    }

}