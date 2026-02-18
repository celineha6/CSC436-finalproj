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
import dev.csse.ceha.mobileapp.Quest
import kotlinx.serialization.Serializable

/*
   "TSApp" = TaskStak App, so "NApp" = Nurture App

   I'm probably gonna be copying a lot of code from
   the to-do list lab (lab 3) since it's similar to our
   project. I've renamed some of the variables though,
   like "TaskDetail" to "QuestInfo" and such.

   Since you made the home screen code, idk how to move it
   but it should be refactored to the new HomeScreen.kt file
   (I'm also copying the file structure from lab 3).
*/

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
            val currentQuest: Quest? = model.findQuestById(currentInfo.id)

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
                if (currentQuest != null) QuestInfoScreen(
                    currentQuest,
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
