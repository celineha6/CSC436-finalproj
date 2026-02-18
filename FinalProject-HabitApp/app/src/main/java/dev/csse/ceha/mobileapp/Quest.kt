package dev.csse.ceha.mobileapp

import java.util.Date

var lastQuestId = 0

data class Quest (
    val id: Int = lastQuestId++,
    val title: String = "",
    val description: String? = null,
    val due: Date? = null,
    val completed: Boolean = false,
    val tags: List<String> = listOf()
)