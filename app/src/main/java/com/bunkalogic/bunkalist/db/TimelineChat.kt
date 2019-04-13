package com.bunkalogic.bunkalist.db

import java.util.*
import kotlin.collections.HashMap

class TimelineChat {
    var userId: String? = null
    var username: String? = null
    var profileImageUrl: String? = null
    var sentAt: Date = Date()
    var contentChat: String? = null


    constructor(){}

    constructor(
        userId: String,
        username: String,
        profileImageUrl: String,
        sentAt: Date,
        contentChat: String
    ) {
        this.userId = userId
        this.username = username
        this.profileImageUrl = profileImageUrl
        this.sentAt = sentAt
        this.contentChat =contentChat

    }

    constructor(
        username: String,
        profileImageUrl: String,
        sentAt: Date,
        contentChat: String
    ) {
        this.username = username
        this.profileImageUrl = profileImageUrl
        this.sentAt = sentAt
        this.contentChat = contentChat

    }

    fun toMap(): Map<String, Any>{

        val result = HashMap<String, Any>()
        result["username"] = username!!
        result["profileImageUrl"] = profileImageUrl!!
        result["sentAt"] = sentAt
        result["contentReview"] = contentChat!!

        return result
    }
}