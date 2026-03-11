package dev.csse.ceha.mobileapp.ui

import androidx.annotation.DrawableRes

data class ShopItem(
    val id: String,
    val name: String,
    val cost: Int, // Quests and stuff should award xp AND gold or some currency
    @DrawableRes val iconRes: Int,
    val description: String = ""
)