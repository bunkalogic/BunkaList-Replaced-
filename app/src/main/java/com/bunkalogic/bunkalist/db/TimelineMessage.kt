package com.bunkalogic.bunkalist.db

import java.util.*

data class TimelineMessage(
    val userId: String = "",
    val username: String = "",
    val profileImageUrl: String = "",
    val sentAt: Date = Date(),
    val oeuvreName: String = "",
    val numSeason: String = "",
    val numChapter: String = "",
    val content: String = ""
)