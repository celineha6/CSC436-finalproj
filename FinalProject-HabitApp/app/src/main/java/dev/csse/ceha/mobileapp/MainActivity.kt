package dev.csse.ceha.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.ceha.mobileapp.ui.theme.MobileAppTheme

// TODO: Move the home screen code to HomeScreen.kt so that we can
//  add it to Routes and save MainActivity for other stuff
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAppTheme {
                MobileAppScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
@Composable
fun MobileAppScreen(
    modifier: Modifier = Modifier
) {
    val pageBackground = Color(0xFF1D5A46)
    val headerBackground = Color(0xFF21473B)
    val lightButton = Color(0xFFC4D6B7)
    val outlineColor = Color(0xFFCDDCC7)
    val textColor = Color(0xFFF2F5F3)

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
                text = "nurture.",
                color = textColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
            Text(
                text = "≡",
                color = textColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp, vertical = 34.dp)
        ) {
            Text(
                text = "MAY 23, 2024",
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
                    Text(text = "", fontSize = 37.sp)
                }
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = "Character Name", color = textColor, fontSize = 18.sp)
                    Text(text = "LVL 1", color = textColor, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HabitTab(
                    label = "Water",
                    selected = true,
                    selectedColor = lightButton,
                    outlineColor = outlineColor,
                    textColor = Color(0xFF1A2D27),
                    modifier = Modifier.weight(1f)
                )
                HabitTab(
                    label = "Meals",
                    selected = false,
                    selectedColor = lightButton,
                    outlineColor = outlineColor,
                    textColor = textColor,
                    modifier = Modifier.weight(1f)
                )
                HabitTab(
                    label = "Exercise",
                    selected = false,
                    selectedColor = lightButton,
                    outlineColor = outlineColor,
                    textColor = textColor,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "0 completed", color = textColor, fontSize = 13.sp)
                Text(text = "  •  ", color = textColor, fontSize = 13.sp)
                Text(text = "Clear", color = Color(0xFF29A9FF), fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            listOf(
                "drink water at 10am",
                "drink water at 12pm",
                "drink water at 2pm",
                "drink water at 4pm",
                "drink water at 6pm"
            ).forEach { item ->
                HabitChecklistItem(item = item, textColor = textColor)
                Spacer(modifier = Modifier.height(10.dp))
            }
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
    textColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(Color(0xFFE8ECEA))
        )
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
fun MobileAppPreview() {
    MobileAppTheme {
        MobileAppScreen(modifier = Modifier.fillMaxSize())
    }
}