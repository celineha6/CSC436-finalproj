package dev.csse.ceha.mobileapp.data

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.csse.ceha.mobileapp.ui.ShopItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val USER_INVENTORY_NAME = "user_inventory"

data class ExpInfo(
		val totalValue: Int,
		val currentValue: Int,
		val untilNextLevel: Int
)

data class InventoryItem(
		val id: String,
		val name: String,
		val cost: Int,
		@DrawableRes val iconRes: Int,
		val description: String = ""
)

class UserInventoryRepository(
		private val context: Context
) {
		companion object {
				private val Context.dataStore by preferencesDataStore(
						name = USER_INVENTORY_NAME
				)

				private object PreferenceKeys {
						val CURRENT_TOTALVALUE = intPreferencesKey("totalValue")
						val CURRENT_VALUE = intPreferencesKey("currentValue")
						val CURRENT_UNTILNEXTLEVEL = intPreferencesKey("untilNextLevel")

						val CURRENT_ID = stringPreferencesKey("id")
						val CURRENT_NAME = stringPreferencesKey("name")
						val CURRENT_COST = intPreferencesKey("cost")
						val CURRENT_ICONRES = intPreferencesKey("iconRes")
						val CURRENT_DESCRIPTION = stringPreferencesKey("description")
				}
		}

		val expFlow = context.dataStore.data.map { prefs ->
				ExpInfo(
						totalValue = prefs[PreferenceKeys.CURRENT_TOTALVALUE] ?: 0,
						currentValue = prefs[PreferenceKeys.CURRENT_VALUE] ?: 0,
						untilNextLevel = prefs[PreferenceKeys.CURRENT_UNTILNEXTLEVEL] ?: 0
				)
		}

		val inventoryFlow = context.dataStore.data.map { prefs ->
				InventoryItem(
						id = prefs[PreferenceKeys.CURRENT_ID] ?: "No ID found",
						name = prefs[PreferenceKeys.CURRENT_NAME] ?: "No NAME found",
						cost = prefs[PreferenceKeys.CURRENT_COST] ?: 0,
						iconRes = prefs[PreferenceKeys.CURRENT_ICONRES] ?: 0,
						description = prefs[PreferenceKeys.CURRENT_DESCRIPTION] ?: "No DESCRIPTION found"
				)
		}
}