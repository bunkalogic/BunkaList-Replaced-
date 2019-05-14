package com.bunkalogic.bunkalist.Retrofit.Response.People

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PeopleSocialMediaResponse {

    @SerializedName("imdb_id")
    @Expose
    var imdbId: String? = null
    @SerializedName("facebook_id")
    @Expose
    var facebookId: Any? = null
    @SerializedName("freebase_mid")
    @Expose
    var freebaseMid: String? = null
    @SerializedName("freebase_id")
    @Expose
    var freebaseId: Any? = null
    @SerializedName("tvrage_id")
    @Expose
    var tvrageId: Int? = null
    @SerializedName("twitter_id")
    @Expose
    var twitterId: Any? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param id
     * @param tvrageId
     * @param freebaseId
     * @param facebookId
     * @param twitterId
     * @param imdbId
     * @param freebaseMid
     */
    constructor(
        imdbId: String,
        facebookId: Any,
        freebaseMid: String,
        freebaseId: Any,
        tvrageId: Int?,
        twitterId: Any,
        id: Int?
    ) : super() {
        this.imdbId = imdbId
        this.facebookId = facebookId
        this.freebaseMid = freebaseMid
        this.freebaseId = freebaseId
        this.tvrageId = tvrageId
        this.twitterId = twitterId
        this.id = id
    }

}