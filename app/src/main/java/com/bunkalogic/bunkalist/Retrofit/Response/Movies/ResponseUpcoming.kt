package com.bunkalogic.bunkalist.Retrofit.Response.Movies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ResponseUpcoming {

    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<ResultMovie>? = null
    @SerializedName("dates")
    @Expose
    var dates: Dates? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param results
     * @param totalResults
     * @param dates
     * @param page
     * @param totalPages
     */
    constructor(page: Int?, results: List<ResultMovie>, dates: Dates, totalPages: Int?, totalResults: Int?) : super() {
        this.page = page
        this.results = results
        this.dates = dates
        this.totalPages = totalPages
        this.totalResults = totalResults
    }

}