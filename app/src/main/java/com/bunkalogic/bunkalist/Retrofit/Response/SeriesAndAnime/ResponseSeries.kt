package com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ResponseSeries {

    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<ResultSeries>? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    constructor(page: Int?, results: List<ResultSeries>, totalResults: Int?, totalPages: Int?) : super() {
        this.page = page
        this.results = results
        this.totalResults = totalResults
        this.totalPages = totalPages
    }

}