package dev.csse.ceha.mobileapp.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dev.csse.ceha.mobileapp.Quest
import java.util.Date

class NViewModel: ViewModel() {

    val questList = mutableStateListOf<Quest>()
    val tagList = mutableStateListOf("Life", "School", "Work", "Fun")
    val completedQuestsExist: Boolean
        get() = questList.count { it.completed } > 0

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