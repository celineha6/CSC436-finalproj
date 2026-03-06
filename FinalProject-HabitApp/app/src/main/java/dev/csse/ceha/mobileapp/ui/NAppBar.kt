package dev.csse.ceha.mobileapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.ceha.mobileapp.ui.theme.MobileAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NAppBar(
    model: NViewModel = viewModel<NViewModel>(),

    canNavigateBack: Boolean = false,
    onUpClick: () -> Unit = {},

    canDeleteHabitItems: Boolean = false,
    onDeleteRequest: () -> Unit = {},
) {
		val scrolledContainerColor = null
		TopAppBar(
        // TODO: Make our own unique styling (I copied this one from TaskStak)
        title = {
						Text(
								text = "nurture.",
								style = MaterialTheme.typography.titleLarge,
								color = MaterialTheme.colorScheme.primary
						)
				},
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = { onUpClick() },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (canDeleteHabitItems) {
                IconButton(
                    onClick = { if (model.completedHabitItemsExist) onDeleteRequest() }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Remove completed task"
                    )
                }
            }
        },
				colors = TopAppBarColors(
						containerColor = MaterialTheme.colorScheme.primary,
						scrolledContainerColor = MaterialTheme.colorScheme.secondary,
						navigationIconContentColor = MaterialTheme.colorScheme.tertiary,
						actionIconContentColor = MaterialTheme.colorScheme.tertiary,
						titleContentColor = MaterialTheme.colorScheme.primary
				)
		)
		/*
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

		 */
		}

@Preview
@Composable
fun TSAppBarPreview() {
    MobileAppTheme {
        Scaffold(
            topBar = { NAppBar() }
        ) {
            innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}