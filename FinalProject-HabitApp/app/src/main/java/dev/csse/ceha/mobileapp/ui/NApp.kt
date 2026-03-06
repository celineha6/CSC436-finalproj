package dev.csse.ceha.mobileapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.csse.ceha.mobileapp.HabitItem
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object Home

		@Serializable
		data object Profile

		@Serializable
		data object Shop

		/*
    @Serializable
    data class QuestInfo(
        val id: String
    )

		 */
}

@Composable
fun NApp(
    model: NViewModel = viewModel<NViewModel>()
) {
    val navController = rememberNavController()

		Scaffold(
				topBar = {
						NAppBar(
								model = model,
								canNavigateBack = true,
								onUpClick = {
										navController.navigate(Routes.Home)
								}
						)
				},
				bottomBar = { NNavBar(navController) }
		) { innerPadding ->
				NavHost(
						navController = navController,
						startDestination = Routes.Home,
						modifier = Modifier
								.padding(innerPadding)
								.fillMaxSize()
				) {
						composable<Routes.Home> {
								HomeScreen(
										model = model,
										modifier = Modifier
												.fillMaxSize()
								)
						}
				}

				/*
        composable<Routes.QuestInfo> {
            // TODO: Make QuestInfoScreen, akin to TaskDetailScreen
        }

				 */
    }
}

enum class AppScreen(val route: Any, val title: String, val icon: ImageVector) {
		HOME(
				dev.csse.ceha.mobileapp.ui.Routes.Home,
				"Home",
				Icons.Default.Home
		),
		PROFILE(
				dev.csse.ceha.mobileapp.ui.Routes.Profile,
				"Local",
				Icons.Default.Person
		),
		SHOP(
				dev.csse.ceha.mobileapp.ui.Routes.Shop,
				"Compass",
				Icons.Default.ShoppingCart
		)
}

@Composable
fun NNavBar(
		navController: NavController
) {
		val backStackEntry by navController
				.currentBackStackEntryAsState()
		val currentRoute = backStackEntry?.destination?.route


		NavigationBar {
				AppScreen.entries.forEach { item ->
						val selected = currentRoute?.endsWith(item.route.toString()) == true
						NavigationBarItem(
								selected = selected,
								onClick = {
										navController.navigate(item.route)
								},
								icon = {
										Icon(item.icon, contentDescription = item.title)
								},
								label = {
										Text(item.title)
								})
				}
		}
}
