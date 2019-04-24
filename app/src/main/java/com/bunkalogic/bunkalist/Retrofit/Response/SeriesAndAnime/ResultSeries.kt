package com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ResultSeries {

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("first_air_date")
    @Expose
    var firstAirDate: String? = null
    @SerializedName("origin_country")
    @Expose
    var originCountry: List<String>? = null
    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("original_name")
    @Expose
    var originalName: String? = null

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
     * @param backdropPath
     * @param voteCount
     * @param id
     * @param originCountry
     * @param overview
     * @param posterPath
     * @param name
     * @param firstAirDate
     * @param voteAverage
     * @param popularity
     */
    constructor(
        posterPath: String,
        popularity: Double?,
        id: Int?,
        backdropPath: String,
        voteAverage: Double?,
        overview: String,
        firstAirDate: String,
        originCountry: List<String>,
        genreIds: List<Int>,
        originalLanguage: String,
        voteCount: Int?,
        name: String,
        originalName: String
    ) : super() {
        this.posterPath = posterPath
        this.popularity = popularity
        this.id = id
        this.backdropPath = backdropPath
        this.voteAverage = voteAverage
        this.overview = overview
        this.firstAirDate = firstAirDate
        this.originCountry = originCountry
        this.genreIds = genreIds
        this.originalLanguage = originalLanguage
        this.voteCount = voteCount
        this.name = name
        this.originalName = originalName
    }

}