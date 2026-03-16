package dev.csse.ceha.mobileapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import dev.csse.ceha.mobileapp.HabitItem
import dev.csse.ceha.mobileapp.ui.theme.MobileAppTheme

@Composable
fun HomeScreen(
	model: HomeViewModel = viewModel(
		factory = HomeViewModel.Factory
	),
	modifier: Modifier = Modifier
) {
    var newTaskText by remember { mutableStateOf("") }

    val pageBackground = Color(0xFF1D5A46)
    val headerBackground = Color(0xFF21473B)
    val lightButton = Color(0xFFC4D6B7)
    val outlineColor = Color(0xFFCDDCC7)
		val textColor = Color(0xFFF2F5F3)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp, vertical = 34.dp)
        ) {
            Text(
                text = model.dateLabel,
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(26.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0D1B21)),
                    contentAlignment = Alignment.Center
                ) {
                    // Show selected character emoji
                    val character = model.selectedCharacter
                    Text(text = character?.emoji ?: "🐰", fontSize = 37.sp)
                }
                Column(modifier = Modifier.padding(start = 16.dp)) {
//                    Text(text = model.characterName, color = textColor, fontSize = 18.sp)
				Text(text = model.levelLabel, color = textColor, fontSize = 14.sp)
			}
		}

		Spacer(modifier = Modifier.height(26.dp))

		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			model.habitTabs.forEachIndexed { index, tab ->
				val selected = (tab.title == model.currentTab.title)
				HabitTab(
					label = tab.title,
					selected = selected,
					selectedColor = lightButton,
					outlineColor = outlineColor,
					textColor = if (selected) Color(0xFF1A2D27) else textColor,
					modifier = Modifier
						.weight(1f)
						.clickable { model.selectTab(index) }
				)
			}
		}

		Spacer(modifier = Modifier.height(10.dp))

		Row(verticalAlignment = Alignment.CenterVertically) {
			Text(text = "${model.completedCount} completed", color = textColor, fontSize = 13.sp)
			Text(text = "  •  ", color = textColor, fontSize = 13.sp)
			Text(
				text = "Clear",
				color = Color(0xFF29A9FF),
				fontSize = 13.sp,
				modifier = Modifier.clickable { model.clearCurrentTabCompletions() }
			)
		}

		Spacer(modifier = Modifier.height(8.dp))

		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			OutlinedTextField(
				value = newTaskText,
				onValueChange = { newTaskText = it },
				label = { Text("New task") },
				singleLine = true,
				modifier = Modifier.weight(1f)
			)
			Button(onClick = {
				if (newTaskText != "") {
					val newItem = HabitItem(
						title = newTaskText
					)
					model.addHabitItem(newItem, model.currentTab)
					newTaskText = ""
				}
			}) {
				Text("Add")
			}
		}

		Spacer(modifier = Modifier.height(10.dp))

		model.currentHabitItems.forEach { item ->
			HabitChecklistItem(
				item = item.title,
				checked = item.completed,
				textColor = textColor,
				onToggle = { model.toggleHabitItem(item) }
			)
			Spacer(modifier = Modifier.height(10.dp))
		}
	}
}

@Composable
private fun HabitTab(
	label: String,
	selected: Boolean,
	selectedColor: Color,
	outlineColor: Color,
	textColor: Color,
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
			.clip(RoundedCornerShape(8.dp))
			.background(if (selected) selectedColor else Color.Transparent)
			.border(width = 2.dp, color = outlineColor, shape = RoundedCornerShape(8.dp))
			.padding(vertical = 8.dp),
		contentAlignment = Alignment.Center
	) {
		Text(text = label, color = textColor, fontSize = 16.sp)
	}
}

@Composable
private fun HabitChecklistItem(
	item: String,
	checked: Boolean,
	textColor: Color,
	onToggle: () -> Unit
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier.clickable { onToggle() }
	) {
		Box(
			modifier = Modifier
				.size(14.dp)
				.background(if (checked) Color(0xFF8BC34A) else Color(0xFFE8ECEA)),
			contentAlignment = Alignment.Center
		) {
			if (checked) {
				Text(
					text = "✓",
					color = Color.White,
					fontSize = 10.sp,
					fontWeight = FontWeight.Bold
				)
			}
		}
		Text(
			text = item,
			color = textColor,
			fontSize = 18.sp,
			modifier = Modifier.padding(start = 10.dp)
		)
	}
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
	MobileAppTheme {
		HomeScreen(
			modifier = Modifier.fillMaxSize()
		)
	}
}

