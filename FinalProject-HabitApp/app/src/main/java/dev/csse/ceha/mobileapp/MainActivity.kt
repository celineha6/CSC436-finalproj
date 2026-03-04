package dev.csse.ceha.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.ceha.mobileapp.ui.HomeScreen
import dev.csse.ceha.mobileapp.ui.NViewModel
import dev.csse.ceha.mobileapp.ui.theme.MobileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAppTheme {
                val model: NViewModel = viewModel()
                HomeScreen(
                    model = model,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
