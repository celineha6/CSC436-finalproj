package dev.csse.ceha.mobileapp.ui

import androidx.annotation.DrawableRes

data class ShopItem(
    val name: String,
    val cost: Int, // Quests and stuff should award xp AND gold or some currency
    val iconRes: Int,
    val description: String = ""
)