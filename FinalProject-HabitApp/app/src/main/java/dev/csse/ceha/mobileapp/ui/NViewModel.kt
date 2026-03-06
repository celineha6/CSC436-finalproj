package dev.csse.ceha.mobileapp.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.csse.ceha.mobileapp.HabitItem
import dev.csse.ceha.mobileapp.HabitTab
import java.util.Date
import kotlin.math.ceil

class NViewModel: ViewModel() {

		private val DEBUG_HABIT_TABS: List<HabitTab> = listOf(
				HabitTab("Water", mutableStateListOf()),
				HabitTab("Meals", mutableStateListOf()),
				HabitTab("Exercise", mutableStateListOf())
		)

    // HomeScreen UI state
    val dateLabel = "MAY 23, 2024"
    val characterName = "Character Name"
    val levelLabel = "LVL 1"

		val habitTabs = DEBUG_HABIT_TABS
    // val habitTabs = mutableStateListOf<HabitTab>()

    var currentTab by mutableStateOf(habitTabs[0])

    val currentHabitItems: List<HabitItem>
        get() = currentTab.habitItems

    val completedCount: Int
        get() = currentHabitItems.count { it.completed }

		val completedHabitItemsExist: Boolean
				get() = completedCount > 0

    // To test how quests look in various interfaces
    init {
        createDebugHabitItems(10)
    }

    // Basics
    fun findHabitItemById(id: String) : HabitItem? {
        return currentHabitItems.find { it.id.toString() == id }
    }

    fun selectTab(index: Int) {
        currentTab = habitTabs[index]
    }

		// Modifying the list of items
    fun toggleHabitItem(item: HabitItem) {
				val oldIndex = currentTab.habitItems.indexOf(item)
				val newItem = item
						.copy(
								completed = !item.completed
						)

				currentTab.habitItems[oldIndex] = newItem
				Log.d("itemAtOldIndex: ${currentTab.habitItems[oldIndex]}", "this is whatever is at the old index")
    }

    fun clearCurrentTabCompletions() {
        for (item in currentTab.habitItems) {
            if (item.completed) {
                toggleHabitItem(item)
            }
        }
    }

    fun addHabitItem(item: HabitItem, tab: HabitTab) {
				tab.habitItems.add(item)
    }

    // Tests
    fun createDebugHabitItems(qty: Int) {
        for (i in 1 .. qty) {
            val title = "Habit $i"
            val description = "Testing a medium-length description for Task $i."
            val date = Date()

            val debugHabitItem = HabitItem(
                title = title,
                description = description,
                due = date
            )

						addHabitItem(debugHabitItem, habitTabs.random())
        }
    }
}