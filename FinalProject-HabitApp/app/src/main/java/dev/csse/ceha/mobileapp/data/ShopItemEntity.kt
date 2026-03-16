package dev.csse.ceha.mobileapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShopItemEntity (
	@PrimaryKey(autoGenerate = true)
	var id: Long = 0,
	val name: String,

	@ColumnInfo(name = "created")
	val cost: Int,
	val iconRes: Int,
	val description: String = ""
)