package dev.csse.ceha.mobileapp.ui

import androidx.annotation.DrawableRes

enum class ItemType {
    HAT,      // Worn on avatar
    THEME,    // Changes app theme/background
    BADGE     // Profile badge
}

data class ShopItem(
    val id: Long,
    val name: String,
    val cost: Int,
    @DrawableRes val iconRes: Int,
    val description: String = "",
    val type: ItemType = ItemType.HAT
)
