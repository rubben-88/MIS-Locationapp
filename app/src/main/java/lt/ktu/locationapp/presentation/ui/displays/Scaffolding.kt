package lt.ktu.locationapp.presentation.ui.displays

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import lt.ktu.locationapp.presentation.navigation.NavigationGraph
import lt.ktu.locationapp.presentation.ui.displays.bars.AppBar
import lt.ktu.locationapp.presentation.ui.displays.bars.NavigationBar
import lt.ktu.locationapp.presentation.ui.displays.screens.MovingProfileScreen
import lt.ktu.locationapp.presentation.viewmodels.ScaffoldingViewModel
import lt.ktu.locationapp.presentation.viewmodels.bars.AppBarViewModel
import lt.ktu.locationapp.presentation.viewmodels.bars.NavigationBarViewModel
import lt.ktu.locationapp.presentation.viewmodels.screens.ProfileViewModel

@Composable
fun Scaffolding(scaffoldingViewModel: ScaffoldingViewModel) {

    val snackBarState: SnackbarDemoAppState = rememberSnackbarDemoAppState()
    val showSnackbar = { message: String ->
        snackBarState.showSnackbar(message = message)
    }

    ScaffoldingMain(
        scaffoldingViewModel = scaffoldingViewModel,
        snackBarState = snackBarState,
        showSnackbar = showSnackbar
    )

    if (scaffoldingViewModel.getOverlay()) {
        ScaffoldingOverlay(
            scaffoldingViewModel = scaffoldingViewModel,
            showSnackbar = showSnackbar
        )
    }

}

@Composable
fun ScaffoldingMain(
    scaffoldingViewModel: ScaffoldingViewModel,
    showSnackbar: (String) -> Unit,
    snackBarState: SnackbarDemoAppState
) {

    val appBarViewModel = remember {
        AppBarViewModel(
            scaffoldingViewModel.dbManager,
            scaffoldingViewModel.navManager,
            showSnackbar = showSnackbar
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarState.scaffoldState.snackbarHostState)
        },
        topBar = {
            AppBar(appBarViewModel)
        },
        bottomBar = {
            NavigationBar(NavigationBarViewModel(scaffoldingViewModel.navManager))
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavigationGraph(
                    scaffoldingViewModel = scaffoldingViewModel,
                    showSnackbar = showSnackbar
                )
            }
        }
    )
}

@Composable
fun ScaffoldingOverlay(
    scaffoldingViewModel: ScaffoldingViewModel,
    showSnackbar: (String) -> Unit,
) {

    val snackBarStateSelf: SnackbarDemoAppState = rememberSnackbarDemoAppState()
    val showSnackbarSelf = { message: String ->
        snackBarStateSelf.showSnackbar(message = message)
    }

    MovingProfileScreen(
        ProfileViewModel(
            scaffoldingViewModel.dbManager,
            scaffoldingViewModel.navManager,
            showSnackbar = showSnackbar,
            showSnackbarSelf = showSnackbarSelf
        ),
        snackBarState = snackBarStateSelf
    )
}