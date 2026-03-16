package dev.csse.ceha.mobileapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopItemDao {
	@Query("SELECT * FROM ShopItemEntity WHERE id = :id")
	fun getShopItem(id: Long): Flow<ShopItemEntity>

	@Query("SELECT * FROM ShopItemEntity ORDER BY created COLLATE NOCASE")
	fun getShopItems(): Flow<List<ShopItemEntity>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addShopItem(entity: ShopItemEntity): Long

	@Update
	fun updateShopItem(entity: ShopItemEntity)

	@Update
	fun removeShopItem(entity: ShopItemEntity)
}