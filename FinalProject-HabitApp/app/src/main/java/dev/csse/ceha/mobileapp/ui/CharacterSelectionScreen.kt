package dev.csse.ceha.mobileapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Character(
    val id: Long,
    val name: String,
    val emoji: String,
    val description: String = ""
)

val availableCharacters = listOf(
    Character(1L, "Rabbit", "🐰", "A cute bouncy friend"),
    Character(2L, "Fox", "🦊", "A clever companion"),
    Character(3L, "Bear", "🐻", "A cozy friend"),
    Character(4L, "Cat", "🐱", "A purrfect buddy"),
    Character(5L, "Dog", "🐕", "A loyal friend"),
    Character(6L, "Owl", "🦉", "A wise companion"),
    Character(7L, "Panda", "🐼", "A peaceful friend"),
    Character(8L, "Koala", "🐨", "An adorable mate")
)

@Composable
fun CharacterSelectionScreen(
    currentCharacter: Character?,
    onSelectCharacter: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    val pageBackground = Color(0xFF1D5A46)
    val headerBackground = Color(0xFF21473B)
    val textColor = Color(0xFFF2F5F3)
    val lightButton = Color(0xFFC4D6B7)
    val outlineColor = Color(0xFFCDDCC7)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(pageBackground)
    ) {
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
                text = "Characters",
                color = textColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
        }

        if (currentCharacter != null) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(2.dp, outlineColor, RoundedCornerShape(14.dp))
                    .background(Color(0xFF174A39))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Current Character",
                    color = textColor.copy(alpha = 0.85f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = currentCharacter.emoji, fontSize = 48.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = currentCharacter.name,
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = "Select a character:",
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(availableCharacters) { character ->
                CharacterCard(
                    character = character,
                    isSelected = currentCharacter?.id == character.id,
                    onClick = { onSelectCharacter(character) },
                    textColor = textColor,
                    outlineColor = outlineColor,
                    accent = lightButton
                )
            }
        }
    }
}

@Composable
private fun CharacterCard(
    character: Character,
    isSelected: Boolean,
    onClick: () -> Unit,
    textColor: Color,
    outlineColor: Color,
    accent: Color
) {
    val cardBg = Color(0xFF174A39)
    val selectedBorder = Color(0xFF4CAF50)

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .border(
                width = if (isSelected) 3.dp else 2.dp,
                color = if (isSelected) selectedBorder else outlineColor,
                shape = RoundedCornerShape(14.dp)
            )
            .background(cardBg)
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(Color(0xFF0D1B21)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = character.emoji, fontSize = 36.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = character.name,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = character.description,
            color = textColor.copy(alpha = 0.7f),
            fontSize = 12.sp
        )

        if (isSelected) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {},
                enabled = false,
                colors = ButtonDefaults.buttonColors(
                    containerColor = selectedBorder,
                    disabledContainerColor = selectedBorder
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Selected", color = Color.White)
            }
        }
    }
}
