package com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime

import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ProductionCompany
import com.bunkalogic.bunkalist.Retrofit.Response.Genre
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Series {

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null
    @SerializedName("created_by")
    @Expose
    var createdBy: List<CreatedBy>? = null
    @SerializedName("episode_run_time")
    @Expose
    var episodeRunTime: List<Int>? = null
    @SerializedName("first_air_date")
    @Expose
    var firstAirDate: String? = null
    @SerializedName("genres")
    @Expose
    var genres: List<Genre>? = null
    @SerializedName("homepage")
    @Expose
    var homepage: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("in_production")
    @Expose
    var inProduction: Boolean? = null
    @SerializedName("languages")
    @Expose
    var languages: List<String>? = null
    @SerializedName("last_air_date")
    @Expose
    var lastAirDate: String? = null
    @SerializedName("last_episode_to_air")
    @Expose
    var lastEpisodeToAir: LastEpisodeToAir? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("next_episode_to_air")
    @Expose
    var nextEpisodeToAir: Any? = null
    @SerializedName("networks")
    @Expose
    var networks: List<Network>? = null
    @SerializedName("number_of_episodes")
    @Expose
    var numberOfEpisodes: Int? = null
    @SerializedName("number_of_seasons")
    @Expose
    var numberOfSeasons: Int? = null
    @SerializedName("origin_country")
    @Expose
    var originCountry: List<String>? = null
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null
    @SerializedName("original_name")
    @Expose
    var originalName: String? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    @SerializedName("production_companies")
    @Expose
    var productionCompanies: List<ProductionCompany>? = null
    @SerializedName("seasons")
    @Expose
    var seasons: List<Season>? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
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
     * @param genres
     * @param originalName
     * @param type
     * @param backdropPath
     * @param voteCount
     * @param id
     * @param nextEpisodeToAir
     * @param numberOfEpisodes
     * @param languages
     * @param originCountry
     * @param inProduction
     * @param name
     * @param posterPath
     * @param voteAverage
     * @param popularity
     * @param networks
     * @param status
     * @param lastAirDate
     * @param lastEpisodeToAir
     * @param numberOfSeasons
     * @param originalLanguage
     * @param homepage
     * @param createdBy
     * @param overview
     * @param seasons
     * @param firstAirDate
     * @param productionCompanies
     * @param episodeRunTime
     */
    constructor(
        backdropPath: String,
        createdBy: List<CreatedBy>,
        episodeRunTime: List<Int>,
        firstAirDate: String,
        genres: List<Genre>,
        homepage: String,
        id: Int?,
        inProduction: Boolean?,
        languages: List<String>,
        lastAirDate: String,
        lastEpisodeToAir: LastEpisodeToAir,
        name: String,
        nextEpisodeToAir: Any,
        networks: List<Network>,
        numberOfEpisodes: Int?,
        numberOfSeasons: Int?,
        originCountry: List<String>,
        originalLanguage: String,
        originalName: String,
        overview: String,
        popularity: Double?,
        posterPath: String,
        productionCompanies: List<ProductionCompany>,
        seasons: List<Season>,
        status: String,
        type: String,
        voteAverage: Double?,
        voteCount: Int?
    ) : super() {
        this.backdropPath = backdropPath
        this.createdBy = createdBy
        this.episodeRunTime = episodeRunTime
        this.firstAirDate = firstAirDate
        this.genres = genres
        this.homepage = homepage
        this.id = id
        this.inProduction = inProduction
        this.languages = languages
        this.lastAirDate = lastAirDate
        this.lastEpisodeToAir = lastEpisodeToAir
        this.name = name
        this.nextEpisodeToAir = nextEpisodeToAir
        this.networks = networks
        this.numberOfEpisodes = numberOfEpisodes
        this.numberOfSeasons = numberOfSeasons
        this.originCountry = originCountry
        this.originalLanguage = originalLanguage
        this.originalName = originalName
        this.overview = overview
        this.popularity = popularity
        this.posterPath = posterPath
        this.productionCompanies = productionCompanies
        this.seasons = seasons
        this.status = status
        this.type = type
        this.voteAverage = voteAverage
        this.voteCount = voteCount
    }

}