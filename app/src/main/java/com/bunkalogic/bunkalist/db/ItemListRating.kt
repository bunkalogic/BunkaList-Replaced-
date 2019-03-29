package com.bunkalogic.bunkalist.db

import java.util.*
import kotlin.collections.HashMap

class ItemListRating{
    var userId: String? = null
    var status: Status? = null
    var oeuvreId: Int? = null
    var addDate: Date = Date()
    var finalRate: Int? = null
    var seasonNumber: Int? = null
    var episodeNumber: Int? = null
    var typeOeuvre: OeuvreType? = null

    constructor(){}

    constructor(
        userId: String,
        status: Status,
        oeuvreId: Int,
        addDate: Date,
        finalRate: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        typeOeuvre: OeuvreType
    ) {
        this.userId = userId
        this.status = status
        this.oeuvreId = oeuvreId
        this.addDate = addDate
        this.finalRate = finalRate
        this.seasonNumber = seasonNumber
        this.episodeNumber = episodeNumber
        this.typeOeuvre = typeOeuvre
    }

    constructor(
        status: Status,
        oeuvreId: Int,
        addDate: Date,
        finalRate: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        typeOeuvre: OeuvreType
    ) {
        this.status = status
        this.oeuvreId = oeuvreId
        this.addDate = addDate
        this.finalRate = finalRate
        this.seasonNumber = seasonNumber
        this.episodeNumber = episodeNumber
        this.typeOeuvre = typeOeuvre
    }

    fun toMap(): Map<String, Any>{
        val result = HashMap<String, Any>()
        result["status"] = status!!
        result["oeuvreId"] = oeuvreId!!
        result["addDate"] = addDate
        result["finalRate"] = finalRate!!
        result["seasonNumber"] = seasonNumber!!
        result["episodeNumber"] = episodeNumber!!
        result["typeOeuvre"] = typeOeuvre!!

        return result
    }


}