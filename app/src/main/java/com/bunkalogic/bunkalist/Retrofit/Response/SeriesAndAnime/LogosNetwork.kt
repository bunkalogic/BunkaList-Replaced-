package com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class LogosNetwork {

    @SerializedName("aspect_ratio")
    @Expose
    var aspectRatio: Double? = null
    @SerializedName("file_path")
    @Expose
    var filePath: String? = null
    @SerializedName("height")
    @Expose
    var height: Int? = null
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("file_type")
    @Expose
    var fileType: String? = null
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null
    @SerializedName("width")
    @Expose
    var width: Int? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param id
     * @param fileType
     * @param height
     * @param filePath
     * @param width
     * @param voteAverage
     * @param aspectRatio
     * @param voteCount
     */
    constructor(
        aspectRatio: Double?,
        filePath: String,
        height: Int?,
        id: String,
        fileType: String,
        voteAverage: Double?,
        voteCount: Int?,
        width: Int?
    ) : super() {
        this.aspectRatio = aspectRatio
        this.filePath = filePath
        this.height = height
        this.id = id
        this.fileType = fileType
        this.voteAverage = voteAverage
        this.voteCount = voteCount
        this.width = width
    }

}