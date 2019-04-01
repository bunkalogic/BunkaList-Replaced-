package com.bunkalogic.bunkalist.db

import java.util.*
import kotlin.collections.HashMap

class ItemListRating{

    var userId: String? = null
    var status: Int? = null
    var oeuvreId: Int? = null
    var addDate: Date = Date()
    var historyRate: Float? = null
    var characterRate: Float? = null
    var effectsRate: Float? = null
    var soundtrackRate: Float? = null
    var enjoymentRate: Float? = null
    var finalRate: Float? = null
    var seasonNumber: String? = null
    var episodeNumber: String? = null
    var typeOeuvre: Int? = null

    constructor(){}

    constructor(
        userId: String?,
        status: Int?,
        oeuvreId: Int?,
        addDate: Date,
        historyRate: Float?,
        characterRate: Float?,
        effectsRate: Float?,
        soundtrackRate: Float?,
        enjoymentRate: Float?,
        finalRate: Float?,
        seasonNumber: String?,
        episodeNumber: String?,
        typeOeuvre: Int?
    ) {
        this.userId = userId
        this.status = status
        this.oeuvreId = oeuvreId
        this.addDate = addDate
        this.historyRate = historyRate
        this.characterRate = characterRate
        this.effectsRate = effectsRate
        this.soundtrackRate = soundtrackRate
        this.enjoymentRate = enjoymentRate
        this.finalRate = finalRate
        this.seasonNumber = seasonNumber
        this.episodeNumber = episodeNumber
        this.typeOeuvre = typeOeuvre
    }

    constructor(
        status: Int?,
        oeuvreId: Int?,
        addDate: Date,
        historyRate: Float?,
        characterRate: Float?,
        effectsRate: Float?,
        soundtrackRate: Float?,
        enjoymentRate: Float?,
        finalRate: Float?,
        seasonNumber: String?,
        episodeNumber: String?,
        typeOeuvre: Int?
    ) {
        this.status = status
        this.oeuvreId = oeuvreId
        this.addDate = addDate
        this.historyRate = historyRate
        this.characterRate = characterRate
        this.effectsRate = effectsRate
        this.soundtrackRate = soundtrackRate
        this.enjoymentRate = enjoymentRate
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
        result["historyRate"] = historyRate!!
        result["characterRate"] = characterRate!!
        result["effectsRate"] = effectsRate!!
        result["soundtrackRate"] = soundtrackRate!!
        result["enjoymentRate"] = enjoymentRate!!
        result["finalRate"] = finalRate!!
        result["seasonNumber"] = seasonNumber!!
        result["episodeNumber"] = episodeNumber!!
        result["typeOeuvre"] = typeOeuvre!!

        return result
    }


}