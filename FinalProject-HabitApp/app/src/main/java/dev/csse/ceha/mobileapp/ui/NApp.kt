package dev.csse.ceha.mobileapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.csse.ceha.mobileapp.HabitItem
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object HomeScreen

    @Serializable
    data class QuestInfo(
        val id: String
    )
}

@Composable
fun NApp(
    model: NViewModel = viewModel<NViewModel>()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen,
    ) {
        composable<Routes.HomeScreen> {

            backStackEntry ->
            val currentInfo: Routes.QuestInfo = backStackEntry.toRoute()
            val currentHabitItem: HabitItem? = model.findHabitItemById(currentInfo.id)

            Scaffold(
                topBar = {
                    NAppBar(
                        model = model,
                        canNavigateBack = true,
                        onUpClick = {
                            navController.navigate(Routes.HomeScreen)
                        }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) {
                innerPadding ->
                if (currentHabitItem != null) EditHabitItemScreen(
                    currentHabitItem,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    model = model
                )
            }
        }

        composable<Routes.QuestInfo> {
            // TODO: Make QuestInfoScreen, akin to TaskDetailScreen
        }
    }
}
