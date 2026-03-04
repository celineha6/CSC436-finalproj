package dev.csse.ceha.mobileapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.csse.ceha.mobileapp.Quest
import java.util.Date

data class HabitItem(
    val label: String,
    val completed: Boolean = false
)

class NViewModel: ViewModel() {

    val questList = mutableStateListOf<Quest>()
    val tagList = mutableStateListOf("Life", "School", "Work", "Fun")
    val completedQuestsExist: Boolean
        get() = questList.count { it.completed } > 0

    // HomeScreen UI state
    val dateLabel = "MAY 23, 2024"
    val characterName = "Character Name"
    val levelLabel = "LVL 1"
    val habitTabs = listOf("Water", "Meals", "Exercise")

    var selectedTabIndex by mutableStateOf(0)
        private set

    private val tabHabitItems = listOf(
        mutableStateListOf(
            HabitItem("drink water at 10am"),
            HabitItem("drink water at 12pm"),
            HabitItem("drink water at 2pm"),
            HabitItem("drink water at 4pm"),
            HabitItem("drink water at 6pm")
        ),
        mutableStateListOf(
            HabitItem("eat breakfast"),
            HabitItem("eat lunch"),
            HabitItem("eat dinner")
        ),
        mutableStateListOf(
            HabitItem("stretch for 10 minutes"),
            HabitItem("go for a walk"),
            HabitItem("light workout")
        )
    )

    val currentHabitItems: List<HabitItem>
        get() = tabHabitItems[selectedTabIndex]

    val completedCount: Int
        get() = currentHabitItems.count { it.completed }

    // To test how quests look in various interfaces
    init {
        createDebugQuests(10)
    }

    // Basics
    fun findQuestById(id: String) : Quest? {
        return questList.find { it.id.toString() == id }
    }

    fun addQuest(q: Quest) {
        questList.add(q)
    }

    fun selectTab(index: Int) {
        selectedTabIndex = index.coerceIn(0, habitTabs.lastIndex)
    }

    fun toggleHabitItem(index: Int) {
        val items = tabHabitItems[selectedTabIndex]
        if (index !in items.indices) return

        val old = items[index]
        items[index] = old.copy(completed = !old.completed)
    }

    fun clearCurrentTabCompletions() {
        val items = tabHabitItems[selectedTabIndex]
        for (i in items.indices) {
            if (items[i].completed) {
                items[i] = items[i].copy(completed = false)
            }
        }
    }

    fun addHabitItem(label: String) {
        val trimmed = label.trim()
        if (trimmed.isEmpty()) return

        tabHabitItems[selectedTabIndex].add(HabitItem(trimmed))
    }

    fun deleteQuest(q: Quest) {
        questList.remove(q)
    }

    // Edits to existing tasks
    fun toggleQuestCompletion(q: Quest) : Quest {
        val oldIndex = questList.indexOf(q)
        val newQuest = q.copy(
            completed = !q.completed
        )

        questList[oldIndex] = newQuest

        return newQuest
    }

    fun setQuestDueDate(q: Quest, d: Date) : Quest {
        val oldIndex = questList.indexOf(q)
        val newQuest = q.copy(
            due = d
        )

        questList[oldIndex] = newQuest

        return newQuest
    }

    // Tests
    fun createDebugQuests(qty: Int) {
        for (i in 1 .. qty) {
            val title = "Task $i"
            val description = "Testing a medium-length description for Task $i."
            val tags = tagList.slice((i%2)..(1+i%3))
            val date = Date()

            val debugQuest = Quest(
                title = title,
                description = description,
                tags = tags,
                due = date
            )

            addQuest(debugQuest)
        }
    }
}