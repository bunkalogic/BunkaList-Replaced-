package com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class LastEpisodeToAir {

    @SerializedName("air_date")
    @Expose
    var airDate: String? = null
    @SerializedName("episode_number")
    @Expose
    var episodeNumber: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("production_code")
    @Expose
    var productionCode: String? = null
    @SerializedName("season_number")
    @Expose
    var seasonNumber: Int? = null
    @SerializedName("show_id")
    @Expose
    var showId: Int? = null
    @SerializedName("still_path")
    @Expose
    var stillPath: String? = null
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param stillPath
     * @param id
     * @param airDate
     * @param overview
     * @param productionCode
     * @param name
     * @param showId
     * @param voteAverage
     * @param seasonNumber
     * @param episodeNumber
     * @param voteCount
     */
    constructor(
        airDate: String,
        episodeNumber: Int?,
        id: Int?,
        name: String,
        overview: String,
        productionCode: String,
        seasonNumber: Int?,
        showId: Int?,
        stillPath: String,
        voteAverage: Double?,
        voteCount: Int?
    ) : super() {
        this.airDate = airDate
        this.episodeNumber = episodeNumber
        this.id = id
        this.name = name
        this.overview = overview
        this.productionCode = productionCode
        this.seasonNumber = seasonNumber
        this.showId = showId
        this.stillPath = stillPath
        this.voteAverage = voteAverage
        this.voteCount = voteCount
    }

}