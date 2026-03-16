package dev.csse.ceha.mobileapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
	entities = [
		ShopItemEntity::class
	],
	version = 1,
	exportSchema = false
)
abstract class ShopItemDatabase : RoomDatabase() {
	abstract fun shopItemDao(): ShopItemDao
}