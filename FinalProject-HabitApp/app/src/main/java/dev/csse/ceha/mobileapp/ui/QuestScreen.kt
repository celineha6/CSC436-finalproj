package dev.csse.ceha.mobileapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QuestProgressScreen() {

    val quests = listOf(
        Triple("Drink Water", 0.6f, "3 / 5"),
        Triple("Exercise", 0.8f, "4 / 5"),
        Triple("Read", 0.2f, "1 / 5")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        quests.forEach { (title, progress, progressText) ->
            QuestProgressItem(
                title = title,
                progress = progress,
                progressText = progressText,
                textColor = Color.White,
                trackColor = Color.DarkGray,
                fillColor = Color.Green,
                mutedTextColor = Color.LightGray
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}