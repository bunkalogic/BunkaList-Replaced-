package com.bunkalogic.bunkalist.Retrofit.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GuestSession {

    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("guest_session_id")
    @Expose
    var guestSessionId: String? = null
    @SerializedName("expires_at")
    @Expose
    var expiresAt: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param guestSessionId
     * @param expiresAt
     * @param success
     */
    constructor(success: Boolean?, guestSessionId: String, expiresAt: String) : super() {
        this.success = success
        this.guestSessionId = guestSessionId
        this.expiresAt = expiresAt
    }

}