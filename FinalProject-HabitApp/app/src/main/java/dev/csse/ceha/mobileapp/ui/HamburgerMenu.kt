package dev.csse.ceha.mobileapp.ui
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class MenuAction(
    val label: String,
    val onClick: () -> Unit
)

@Composable
fun HamburgerMenu(
    actions: List<MenuAction>,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.White,
    iconSizeSp: Int = 30,
    iconText: String = "≡"
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Text(
            text = iconText,
            color = iconColor,
            fontSize = iconSizeSp.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            actions.forEach { action ->
                DropdownMenuItem(
                    text = { Text(action.label) },
                    onClick = {
                        expanded = false
                        action.onClick()
                    }
                )
            }
        }
    }
}