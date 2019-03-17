package com.bunkalogic.bunkalist.db

import java.util.*
import java.util.HashMap

class TimelineMessage{
    var userId: String? = null
    var username: String? = null
    var profileImageUrl: String = ""
    var sentAt: Date = Date()
    var oeuvreName: String? = null
    var numSeason: String? = null
    var numEpisode: String? = null
    var content: String? = null
    var numPositive: String? = null
    var numNegative: String? = null

    constructor(){}

    constructor(
        userId: String,
        username: String,
        profileImageUrl: String,
        sentAt: Date,
        oeuvreName: String,
        numSeason: String,
        numEpisode: String,
        content: String,
        numPositive: String,
        numNegative: String
    ) {
        this.userId = userId
        this.username = username
        this.profileImageUrl = profileImageUrl
        this.sentAt = sentAt
        this.oeuvreName = oeuvreName
        this.numSeason = numSeason
        this.numEpisode = numEpisode
        this.content = content
        this.numPositive = numPositive
        this.numNegative = numNegative
    }

    constructor(
        username: String,
        profileImageUrl: String,
        sentAt: Date,
        oeuvreName: String,
        numSeason: String,
        numEpisode: String,
        content: String,
        numPositive: String,
        numNegative: String
    ) {
        this.username = username
        this.profileImageUrl = profileImageUrl
        this.sentAt = sentAt
        this.oeuvreName = oeuvreName
        this.numSeason = numSeason
        this.numEpisode = numEpisode
        this.content = content
        this.numPositive = numPositive
        this.numNegative = numNegative
    }

    fun toMap(): Map<String, Any>{

        val result = HashMap<String, Any>()
        result["username"] = username!!
        result["profileImageUrl"] = profileImageUrl
        result["sentAt"] = sentAt
        result["oeuvreName"] = oeuvreName!!
        result["numSeason"] = numSeason!!
        result["numEpisode"] = numEpisode!!
        result["content"] = content!!
        result["numPositive"] = numPositive!!
        result["numNegative"] = numNegative!!

        return result
    }


}