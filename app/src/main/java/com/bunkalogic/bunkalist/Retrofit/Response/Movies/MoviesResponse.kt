package com.bunkalogic.bunkalist.Retrofit.Response.Movies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MoviesResponse {

    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<ResultMovie>? = null

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
    constructor(page: Int?, totalResults: Int?, totalPages: Int?, results: List<ResultMovie>) : super() {
        this.page = page
        this.totalResults = totalResults
        this.totalPages = totalPages
        this.results = results
    }

}