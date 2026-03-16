package dev.csse.ceha.mobileapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.csse.ceha.mobileapp.R
import dev.csse.ceha.mobileapp.ui.theme.MobileAppTheme



@Composable
fun ShopScreen(
    model: NViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    ShopContent(
        xp = model.xp,
        items = model.shopItems,
        isOwned = { id -> model.isOwned(id) },
        isEquipped = { id -> model.isEquipped(id) },
        onBuy = { item -> model.purchase(item) },
        onEquip = { item -> model.equipItem(item) },
        modifier = modifier,
    )
}


@Composable
private fun ShopContent(
    xp: Int,
    items: List<ShopItem>,
    isOwned: (String) -> Boolean,
    isEquipped: (String) -> Boolean,
    onBuy: (ShopItem) -> Boolean,
    onEquip: (ShopItem) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val pageBackground = Color(0xFF1D5A46)
    val headerBackground = Color(0xFF21473B)
    val textColor = Color(0xFFF2F5F3)
    val lightButton = Color(0xFFC4D6B7)
    val outlineColor = Color(0xFFCDDCC7)

    var pendingBuy: ShopItem? by remember { mutableStateOf(null) }
    var lastMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(pageBackground)
    ) {
        // Header (same vibe as Home)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerBackground)
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "nurture.",
                color = textColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
        }

        // XP balance block
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .border(2.dp, outlineColor, RoundedCornerShape(14.dp))
                .background(Color(0xFF174A39))
                .padding(16.dp)
        ) {
            Text(
                text = "XP BALANCE",
                color = textColor.copy(alpha = 0.85f),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "$xp XP",
                color = textColor,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            if (lastMessage.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = lastMessage,
                    color = textColor.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
            }
        }

        // Shop grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(items) { item ->
                val owned = isOwned(item.id)
                val equipped = isEquipped(item.id)
                val affordable = xp >= item.costXp

                ShopItemCard(
                    item = item,
                    owned = owned,
                    equipped = equipped,
                    affordable = affordable,
                    textColor = textColor,
                    outlineColor = outlineColor,
                    accent = lightButton,
                    onClickBuy = {
                        if (!owned) {
                            if (affordable) {
                                pendingBuy = item
                            } else {
                                lastMessage = "Not enough XP for ${item.name}."
                            }
                        }
                    },
                    onClickEquip = {
                        if (owned && !equipped) {
                            val success = onEquip(item)
                            if (success) {
                                lastMessage = "Equipped ${item.name}!"
                            }
                        }
                    }
                )
            }
        }
    }

    // Confirm purchase dialog
    if (pendingBuy != null) {
        val item = pendingBuy!!

        AlertDialog(
            onDismissRequest = { pendingBuy = null },
            title = { Text("Purchase item?") },
            text = { Text("Buy ${item.name} for ${item.costXp} XP?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val success = onBuy(item)
                        lastMessage = if (success) "Purchased ${item.name}!" else "Purchase failed."
                        pendingBuy = null
                    }
                ) { Text("Buy") }
            },
            dismissButton = {
                TextButton(onClick = { pendingBuy = null }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun ShopItemCard(
    item: ShopItem,
    owned: Boolean,
    equipped: Boolean,
    affordable: Boolean,
    textColor: Color,
    outlineColor: Color,
    accent: Color,
    onClickBuy: () -> Unit,
    onClickEquip: () -> Unit
) {
    val cardBg = Color(0xFF174A39)

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .border(2.dp, outlineColor, RoundedCornerShape(14.dp))
            .background(cardBg)
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        // Icon placeholder (you can swap with Image later)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF0D1B21)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🧸", fontSize = 28.sp) // temporary placeholder
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = item.name,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "${item.costXp} XP",
            color = textColor.copy(alpha = 0.85f),
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Show Equip button if owned but not equipped, otherwise show Buy/Need XP
        if (owned && !equipped) {
            Button(
                onClick = onClickEquip,
                colors = ButtonDefaults.buttonColors(
                    containerColor = accent
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Equip",
                    color = Color(0xFF1A2D27)
                )
            }
        } else if (equipped) {
            Button(
                onClick = {},
                enabled = false,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A6B52),
                    disabledContainerColor = Color(0xFF4A6B52)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Equipped",
                    color = textColor
                )
            }
        } else {
            val buttonText = if (affordable) "Buy" else "Need XP"
            Button(
                onClick = onClickBuy,
                enabled = affordable,
                colors = ButtonDefaults.buttonColors(
                    containerColor = accent,
                    disabledContainerColor = Color(0xFF2A3F38)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buttonText,
                    color = if (affordable) Color(0xFF1A2D27) else textColor
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun ShopScreenPreview() {
    MobileAppTheme {
        ShopContent(
            xp = 320,
            items = listOf(
                ShopItem(
                    id = "rabbit_hat",
                    name = "Rabbit Hat",
                    costXp = 120,
                    iconRes = R.drawable.rabbit,
                    description = "A cute pixel bunny hat",
                    type = ItemType.HAT
                ),
                ShopItem(
                    id = "leaf_badge",
                    name = "Leaf Badge",
                    costXp = 80,
                    iconRes = R.drawable.rabbit,
                    description = "Nature-themed profile badge",
                    type = ItemType.BADGE
                ),
                ShopItem(
                    id = "forest_bg",
                    name = "Forest Theme",
                    costXp = 400,
                    iconRes = R.drawable.rabbit,
                    description = "Unlock a forest style",
                    type = ItemType.THEME
                ),
                ShopItem(
                    id = "seedling_pet",
                    name = "Seedling Pet",
                    costXp = 220,
                    iconRes = R.drawable.rabbit,
                    description = "A tiny companion",
                    type = ItemType.HAT
                )
            ),
            isOwned = { itemId: String -> itemId == "leaf_badge" },
            isEquipped = { itemId: String -> false },
            onBuy = { _: ShopItem -> false },
            onEquip = { _: ShopItem -> true }
        )
    }
}
