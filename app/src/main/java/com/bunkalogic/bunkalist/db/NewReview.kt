package com.bunkalogic.bunkalist.db

import java.util.*
import kotlin.collections.HashMap

class NewReview{
    var userId: String? = null
    var username: String? = null
    var profileImageUrl: String? = null
    var sentAt: Date = Date()
    var titleReview: String? = null
    var contentReview: String? = null
    var isSpoiler: Boolean? = false


    constructor(){}

    constructor(
        userId: String,
        username: String,
        profileImageUrl: String,
        sentAt: Date,
        titleReview: String,
        contentReview: String,
        isSpoiler: Boolean
    ) {
        this.userId = userId
        this.username = username
        this.profileImageUrl = profileImageUrl
        this.sentAt = sentAt
        this.titleReview = titleReview
        this.contentReview = contentReview
        this.isSpoiler = isSpoiler
    }

    constructor(
        username: String,
        profileImageUrl: String,
        sentAt: Date,
        titleReview: String,
        contentReview: String,
        isSpoiler: Boolean
    ) {
        this.username = username
        this.profileImageUrl = profileImageUrl
        this.sentAt = sentAt
        this.titleReview = titleReview
        this.contentReview = contentReview
        this.isSpoiler = isSpoiler
    }

    fun toMap(): Map<String, Any>{

        val result = HashMap<String, Any>()
        result["username"] = username!!
        result["profileImageUrl"] = profileImageUrl!!
        result["sentAt"] = sentAt
        result["titleReview"] = titleReview!!
        result["contentReview"] = contentReview!!
        result["isSpoiler"] = isSpoiler!!

        return result
    }


}