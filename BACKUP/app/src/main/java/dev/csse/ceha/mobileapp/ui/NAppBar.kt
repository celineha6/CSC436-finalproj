package dev.csse.ceha.mobileapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.ceha.mobileapp.ui.theme.MobileAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NAppBar(
    model: NViewModel = viewModel<NViewModel>(),

    canNavigateBack: Boolean = false,
    onUpClick: () -> Unit = {},

    canDeleteQuests: Boolean = false,
    onDeleteRequest: () -> Unit = {},
) {
    TopAppBar(
        // TODO: Make our own unique styling (I copied this one from TaskStak)
        title = { Text(
            "Nurture",
            style = MaterialTheme.typography.displayLarge
        ) },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
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
            if (canDeleteQuests) {
                IconButton(
                    onClick = { if (model.completedQuestsExist) onDeleteRequest() }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Remove completed task"
                    )
                }
            }
        }
    )
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