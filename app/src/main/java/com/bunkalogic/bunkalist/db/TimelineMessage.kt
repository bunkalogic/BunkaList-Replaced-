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
    var numPositive: Int = 0
    var isSpoiler: Boolean? = false
    var tokenId: String? = null
    var oeuvreId: Int? = null
    var mediaUrl: String? = null

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
        numPositive: Int,
        isSpoiler: Boolean,
        tokenId: String,
        oeuvreId: Int,
        mediaUrl: String

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
        this.isSpoiler = isSpoiler
        this.tokenId = tokenId
        this.oeuvreId = oeuvreId
        this.mediaUrl = mediaUrl
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
        result["numPositive"] = numPositive
        result["isSpoiler"] = isSpoiler!!
        result["tokenId"] = tokenId!!
        result["oeuvreId"] = oeuvreId!!
        result["mediaUrl"] = mediaUrl!!
        return result
    }


}