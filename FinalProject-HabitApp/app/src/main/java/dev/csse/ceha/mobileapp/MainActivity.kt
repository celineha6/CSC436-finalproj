package dev.csse.ceha.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.csse.ceha.mobileapp.ui.NApp
import dev.csse.ceha.mobileapp.ui.theme.MobileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAppTheme {
                NApp()
            }
        }
    }
}
