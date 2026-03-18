package dev.csse.ceha.mobileapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import dev.csse.ceha.mobileapp.data.ShopItemEntity
import kotlin.random.Random

@Composable
fun ProfileScreen(
	homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
	profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),
	modifier: Modifier = Modifier
) {

	val uiState = profileViewModel.uiState.collectAsStateWithLifecycle().value
	val characterName = uiState.userInfo.name
	val characterLevel = uiState.userInfo.exp.level

	val quests = homeViewModel.questProgress.map { quest ->
		Triple(quest.title, quest.progress, quest.progressText)
	}

	LazyColumn(
		modifier = Modifier
	) {
		item {
			QuestProgressContent(
				characterName = characterName,
				levelLabel = "LVL $characterLevel",
				quests = quests,
				modifier = modifier,
				onComplete = { value ->
					profileViewModel.userRepo.updateGold(value)
				}
			)
		}
		items(uiState.items) { item ->
			ItemCard(item)
		}
	}
}

@Composable
fun ItemCard(
	item: ShopItemEntity,
) {
	val textColor = Color(0xFFF2F5F3)
	val outlineColor = Color(0xFFCDDCC7)
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
	}
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
	onComplete: (Int) -> Unit,
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
					mutedTextColor = Color.LightGray,
					goldReward = Random.nextInt(50, 101),
					onComplete = onComplete
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
		),
		onComplete = {
			fun doNothing(num: Int) {

			}
		}
	)
}
