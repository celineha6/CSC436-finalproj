package dev.csse.ceha.mobileapp

import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.Date

var lastHabitItemId = 0

data class HabitTab (
		val title: String,
		val habitItems: SnapshotStateList<HabitItem>
)

data class HabitItem (
    val id: Int = lastHabitItemId++,
    val title: String,
    val description: String? = null,
    val due: Date? = null,
    val completed: Boolean = false
)