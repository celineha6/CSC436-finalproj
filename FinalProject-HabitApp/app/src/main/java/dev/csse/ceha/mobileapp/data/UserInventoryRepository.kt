package dev.csse.ceha.mobileapp.data

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.csse.ceha.mobileapp.ui.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val USER_INVENTORY_NAME = "user_inventory"

data class ExpInfo(
	val totalValue: Int = 0,
	val level: Int = totalValue / 500,
	val currentValue: Int = totalValue % 500,
	val untilNextLevel: Int = 500 - currentValue,
)

data class UserInfo(
	val name: String = "",
	val exp: ExpInfo = ExpInfo(),
	val gold: Int = 0
)

class UserInventoryRepository(
	private val context: Context
) {
	private val databaseCallback = object : RoomDatabase.Callback() {
		override fun onCreate(db: SupportSQLiteDatabase) {
			super.onCreate(db)
		}
	}

	private val database: ShopItemDatabase = Room.databaseBuilder(
		context,
		ShopItemDatabase::class.java,
		"looper.db"
	)
		.addCallback(databaseCallback)
		.build()

	private val shopItemDao = database.shopItemDao()

	companion object {
		private val Context.dataStore by preferencesDataStore(
			name = USER_INVENTORY_NAME
		)

		private object PreferenceKeys {
			val USER_NAME = stringPreferencesKey("user_name")

			val EXP_TOTAL_VALUE = intPreferencesKey("exp_total")
			val EXP_LEVEL = intPreferencesKey("exp_level")
			val EXP_CURRENT_VALUE = intPreferencesKey("exp_current")
			val EXP_UNTIL_NEXT_LEVEL = intPreferencesKey("exp_until")

			val USER_GOLD_VALUE = intPreferencesKey("gold_value")
		}
	}

	val userFlow = context.dataStore.data.map { prefs ->
		val name = prefs[PreferenceKeys.USER_NAME] ?: ""
		val exp = ExpInfo(
			totalValue = prefs[PreferenceKeys.EXP_TOTAL_VALUE] ?: 0,
			level = prefs[PreferenceKeys.EXP_LEVEL] ?: 0,
			currentValue = prefs[PreferenceKeys.EXP_CURRENT_VALUE] ?: 0,
			untilNextLevel = prefs[PreferenceKeys.EXP_UNTIL_NEXT_LEVEL] ?: 0
		)
		val gold = prefs[PreferenceKeys.USER_GOLD_VALUE] ?: 0

		UserInfo(
			name = name,
			exp = exp,
			gold = gold
		)
	}

	suspend fun updateName(value: String) {
		context.dataStore.edit { prefs ->
			prefs[PreferenceKeys.USER_NAME] = value
		}
	}

	suspend fun updateExp(new: Int) {
		context.dataStore.edit { prefs ->
			val current = prefs[PreferenceKeys.EXP_CURRENT_VALUE] ?: 0
			prefs[PreferenceKeys.EXP_CURRENT_VALUE] = current + new
		}
	}

	suspend fun updateGold(value: Int) {
		context.dataStore.edit { prefs ->
			prefs[PreferenceKeys.USER_GOLD_VALUE] = value
		}
	}

	fun getShopItems() = shopItemDao.getShopItems()

	fun addShopItem(item: ShopItemEntity) {
		CoroutineScope(Dispatchers.IO).launch {
			item.id = shopItemDao.addShopItem(item)
		}
	}
}