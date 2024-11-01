package lt.ktu.locationapp.presentation.ui.displays.bars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import lt.ktu.locationapp.presentation.ui.events.bars.AppBarUiEvents
import lt.ktu.locationapp.presentation.ui.states.bars.AppBarAction
import lt.ktu.locationapp.presentation.ui.states.bars.AppBarNavigation
import lt.ktu.locationapp.presentation.viewmodels.bars.AppBarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(appBarViewModel: AppBarViewModel) {

    val scope = rememberCoroutineScope()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = appBarViewModel.getBackGroundColor()
        ),
        title = {
            Text(
                text = appBarViewModel.getTitle(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            when (appBarViewModel.getNavigation()) {
                AppBarNavigation.BACK -> IconButton(
                    onClick = { appBarViewModel.onEvent(AppBarUiEvents.Back) }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
                AppBarNavigation.CLOSE -> IconButton(
                    onClick = {
                        scope.launch {
                            appBarViewModel.onEvent(AppBarUiEvents.Close)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                    )
                }
                AppBarNavigation.NONE -> Unit
            }
        },
        actions = {
            AppBarAction(
                appBarViewModel,
                appBarViewModel.getAction1(),
                scope,
                appBarViewModel.getAction1Disabled()
            )
            AppBarAction(
                appBarViewModel,
                appBarViewModel.getAction2(),
                scope,
                appBarViewModel.getAction2Disabled()
            )
        }
    )
}

@Composable
fun AppBarAction(
    appBarViewModel: AppBarViewModel,
    action: AppBarAction,
    scope: CoroutineScope,
    disabled: Boolean
) {

    val owner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    return when (action) {
        AppBarAction.PROFILE -> IconButton(onClick = {
            appBarViewModel.onEvent(AppBarUiEvents.OpenProfile)
        }, enabled = !disabled) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile",
            )
        }
        AppBarAction.REFRESH -> IconButton(onClick = {
            appBarViewModel.onEvent(AppBarUiEvents.Refresh(owner))
        }, enabled = !disabled) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Refresh",
            )
        }
        AppBarAction.SAVE -> TextButton(onClick = {
            scope.launch {
                appBarViewModel.onEvent(AppBarUiEvents.Save)
            }
        }, enabled = !disabled) {
            Text("Save")
        }
        AppBarAction.NONE -> Unit
    }
}