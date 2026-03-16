package dev.csse.ceha.mobileapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.ceha.mobileapp.QuestProgressItem
import dev.csse.ceha.mobileapp.R

@Composable
fun ProfileScreen(
	model: ProfileViewModel = viewModel(
		factory = ProfileViewModel.Factory
	),
	modifier: Modifier = Modifier
) {
		val uiState = model.uiState.collectAsStateWithLifecycle()
		val characterName = uiState.value.userInfo.name
		val characterLevel = uiState.value.userInfo.exp.level

    val quests = listOf(
        Triple("Drink Water", 0.6f, "3 / 5"),
        Triple("Exercise", 0.8f, "4 / 5"),
        Triple("Read", 0.2f, "1 / 5")
    )

    QuestProgressContent(
        characterName = characterName,
        levelLabel = "LVL $characterLevel",
        quests = quests,
        modifier = modifier,
    )
}

/**
 * Pure UI composable (no ViewModel). Easy to Preview.
 */
@Composable
fun QuestProgressContent(
    characterName: String,
    levelLabel: String,
    quests: List<Triple<String, Float, String>>,
    modifier: Modifier = Modifier,
) {
    val pageBackground = Color(0xFF1D5A46)
    val headerBackground = Color(0xFF21473B)
    val textColor = Color(0xFFF2F5F3)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(pageBackground)
    ) {
        // Body content (centered)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.rabbit),
                contentDescription = "Avatar",
                modifier = Modifier.height(180.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(12.dp))

            androidx.compose.material3.Text(
                text = characterName,
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            androidx.compose.material3.Text(
                text = levelLabel,
                color = textColor,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            quests.forEach { (title, progress, progressText) ->
                QuestProgressItem(
									title = title,
									progress = progress,
									progressText = progressText,
									textColor = Color.White,
									trackColor = Color.DarkGray,
									fillColor = Color(0xFFC4D6B7),
									mutedTextColor = Color.LightGray
								)

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuestProgressContentPreview() {
    QuestProgressContent(
        characterName = "Character Name",
        levelLabel = "LVL 1",
        quests = listOf(
            Triple("Complete all tasks for 10 days", 0.3f, "3/10"),
            Triple("Try out a new preset", 0.0f, "0/1"),
            Triple("Reach LVL 10", 0.1f, "1/10")
        )
    )
}