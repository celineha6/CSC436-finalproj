package dev.csse.ceha.mobileapp.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.csse.ceha.mobileapp.HabitItem
import dev.csse.ceha.mobileapp.HabitTab
import dev.csse.ceha.mobileapp.data.UserInventoryRepository
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel(
	val userRepo: UserInventoryRepository? = null
): ViewModel() {

	private val DEBUG_HABIT_TABS = mutableStateListOf(
		HabitTab("Water", mutableStateListOf()),
		HabitTab("Meals", mutableStateListOf()),
		HabitTab("Exercise", mutableStateListOf())
	)

    // HomeScreen UI state
    val dateLabel = "MAY 23, 2024"
    val characterName = "Character Name"
    var xp by mutableIntStateOf(0)
        private set

    val level: Int
        get() = (xp / 500) + 1   // 500 XP per level (change if you want)

    val levelLabel: String
        get() = "LVL $level"

	val habitTabs = DEBUG_HABIT_TABS

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
        val wasCompleted = item.completed
		val newItem = item
				.copy(
						completed = !item.completed
				)

		currentTab.habitItems[oldIndex] = newItem
		Log.d("itemAtOldIndex: ${currentTab.habitItems[oldIndex]}", "this is whatever is at the old index")
        // Award XP only when marking complete (not when unchecking)
        if (!wasCompleted) {
			userRepo?.let { repo ->
				viewModelScope.launch {
					repo.updateExp(10) // choose your XP amount
				}
			}
        }
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
    
    // Shop items
    val shopItems = listOf(
        ShopItem(1L, "Bunny Hat", 50, dev.csse.ceha.mobileapp.R.drawable.rabbit, type = ItemType.HAT),
        ShopItem(2L, "Plant Decor", 30, dev.csse.ceha.mobileapp.R.drawable.rabbit, type = ItemType.THEME),
        ShopItem(3L, "Nature Badge", 25, dev.csse.ceha.mobileapp.R.drawable.rabbit, type = ItemType.BADGE)
    )
    private val ownedItemIds = mutableStateListOf<Long>()
    
    // Currently equipped items (one per type)
    private var equippedItemIds: MutableMap<ItemType, Long> = mutableMapOf()

    fun isOwned(itemId: Long): Boolean = ownedItemIds.contains(itemId)

    fun isEquipped(itemId: Long): Boolean = equippedItemIds.values.contains(itemId)
    
    fun getEquippedItem(type: ItemType): ShopItem? {
        val equippedId = equippedItemIds[type] ?: return null
        return shopItems.find { it.id == equippedId }
    }

    fun purchase(item: ShopItem): Boolean {
        if (isOwned(item.id)) return false
        if (xp < item.costXp) return false

        xp -= item.costXp
        ownedItemIds.add(item.id)
        return true
    }
    
    fun equipItem(item: ShopItem): Boolean {
        if (!isOwned(item.id)) return false
        equippedItemIds[item.type] = item.id
        // Trigger recomposition by reassigning
        equippedItemIds = equippedItemIds.toMutableMap()
        return true
    }
    
    fun unequipItem(type: ItemType): Boolean {
        if (equippedItemIds.containsKey(type)) {
            equippedItemIds.remove(type)
            // Trigger recomposition by reassigning
            equippedItemIds = equippedItemIds.toMutableMap()
            return true
        }
        return false
    }

    // Quest progress data - dynamically calculated from habit completion
    data class QuestProgress(
        val title: String,
        val current: Int,
        val target: Int
    ) {
        val progress: Float
            get() = if (target > 0) current.toFloat() / target else 0f
        
        val progressText: String
            get() = "$current / $target"
    }

    // Calculate quest progress from actual habit data
    val questProgress: List<QuestProgress>
        get() {
            val totalCompleted = habitTabs.sumOf { tab -> 
                tab.habitItems.count { it.completed } 
            }
            val totalItems = habitTabs.sumOf { it.habitItems.size }

            return listOf(
                QuestProgress("Complete all tasks", totalCompleted, totalItems.coerceAtLeast(1)),
                QuestProgress("Daily habits", completedCount, currentHabitItems.size.coerceAtLeast(1)),
                QuestProgress("Build streaks", totalCompleted / 3, 10)
            )
        }
}
