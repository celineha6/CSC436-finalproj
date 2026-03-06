package dev.csse.ceha.mobileapp.ui

import androidx.annotation.DrawableRes

data class ShopItem(
    val id: String,
    val name: String,
    val costXp: Int,
    @DrawableRes val iconRes: Int,
    val description: String = ""
)