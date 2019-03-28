package com.bunkalogic.bunkalist.Retrofit.Response.Movies

import com.bunkalogic.bunkalist.Retrofit.Response.Genre
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Movie {

    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null
    @SerializedName("belongs_to_collection")
    @Expose
    var belongsToCollection: Any? = null
    @SerializedName("budget")
    @Expose
    var budget: Int? = null
    @SerializedName("genres")
    @Expose
    var genres: List<Genre>? = null
    @SerializedName("homepage")
    @Expose
    var homepage: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("imdb_id")
    @Expose
    var imdbId: String? = null
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null
    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: Any? = null
    @SerializedName("production_companies")
    @Expose
    var productionCompanies: List<ProductionCompany>? = null
    @SerializedName("production_countries")
    @Expose
    var productionCountries: List<ProductionCountry>? = null
    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null
    @SerializedName("revenue")
    @Expose
    var revenue: Int? = null
    @SerializedName("runtime")
    @Expose
    var runtime: Int? = null
    @SerializedName("spoken_languages")
    @Expose
    var spokenLanguages: List<SpokenLanguage>? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("tagline")
    @Expose
    var tagline: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("video")
    @Expose
    var video: Boolean? = null
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
     * @param budget
     * @param genres
     * @param spokenLanguages
     * @param runtime
     * @param backdropPath
     * @param voteCount
     * @param id
     * @param title
     * @param releaseDate
     * @param posterPath
     * @param originalTitle
     * @param voteAverage
     * @param video
     * @param popularity
     * @param revenue
     * @param productionCountries
     * @param status
     * @param originalLanguage
     * @param adult
     * @param imdbId
     * @param homepage
     * @param overview
     * @param belongsToCollection
     * @param productionCompanies
     * @param tagline
     */
    constructor(
        adult: Boolean?,
        backdropPath: String,
        belongsToCollection: Any,
        budget: Int?,
        genres: List<Genre>,
        homepage: String,
        id: Int?,
        imdbId: String,
        originalLanguage: String,
        originalTitle: String,
        overview: String,
        popularity: Double?,
        posterPath: Any,
        productionCompanies: List<ProductionCompany>,
        productionCountries: List<ProductionCountry>,
        releaseDate: String,
        revenue: Int?,
        runtime: Int?,
        spokenLanguages: List<SpokenLanguage>,
        status: String,
        tagline: String,
        title: String,
        video: Boolean?,
        voteAverage: Double?,
        voteCount: Int?
    ) : super() {
        this.adult = adult
        this.backdropPath = backdropPath
        this.belongsToCollection = belongsToCollection
        this.budget = budget
        this.genres = genres
        this.homepage = homepage
        this.id = id
        this.imdbId = imdbId
        this.originalLanguage = originalLanguage
        this.originalTitle = originalTitle
        this.overview = overview
        this.popularity = popularity
        this.posterPath = posterPath
        this.productionCompanies = productionCompanies
        this.productionCountries = productionCountries
        this.releaseDate = releaseDate
        this.revenue = revenue
        this.runtime = runtime
        this.spokenLanguages = spokenLanguages
        this.status = status
        this.tagline = tagline
        this.title = title
        this.video = video
        this.voteAverage = voteAverage
        this.voteCount = voteCount
    }

}