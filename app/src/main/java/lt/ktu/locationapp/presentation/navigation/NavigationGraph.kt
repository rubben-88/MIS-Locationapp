package lt.ktu.locationapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import lt.ktu.locationapp.domain.models.observationFromString
import lt.ktu.locationapp.presentation.ui.displays.screens.MapScreen
import lt.ktu.locationapp.presentation.ui.displays.screens.ObservationEditScreen
import lt.ktu.locationapp.presentation.ui.displays.screens.ObservationNewScreen
import lt.ktu.locationapp.presentation.ui.displays.screens.ObservationsScreen
import lt.ktu.locationapp.presentation.viewmodels.ScaffoldingViewModel
import lt.ktu.locationapp.presentation.viewmodels.screens.ObservationEditViewModel
import lt.ktu.locationapp.presentation.viewmodels.screens.ObservationNewViewModel
import lt.ktu.locationapp.presentation.viewmodels.screens.ObservationsViewModel

@Composable
fun NavigationGraph(
    scaffoldingViewModel: ScaffoldingViewModel,
    showSnackbar: (String) -> Unit
) {

    LaunchedEffect("") {
        scaffoldingViewModel.navManager.appBarViewModel.onNavigate(startingTopLevelRoute)
    }

    NavHost(
        navController = scaffoldingViewModel.navManager.navController,
        startDestination = startingTopLevelRoute,
    ) {

        composable(Routes.MAP) {
            MapScreen(scaffoldingViewModel.dbManager)
        }

        composable(Routes.OBSERVATIONS) {
            ObservationsScreen(ObservationsViewModel(
                scaffoldingViewModel.dbManager,
                scaffoldingViewModel.navManager
            ))
        }

        composable(Routes.OBSERVATION_NEW) {
            ObservationNewScreen(ObservationNewViewModel(
                scaffoldingViewModel.dbManager,
                scaffoldingViewModel.navManager,
                showSnackbar = showSnackbar
            ))
        }

        composable(
            "${Routes.OBSERVATION_EDIT}/{obs}",
            arguments = listOf(
                navArgument(name = "obs") { type = NavType.StringType },
            )
        ) {
            val obs = it.arguments?.getString("obs")!!
            ObservationEditScreen(ObservationEditViewModel(
                scaffoldingViewModel.dbManager,
                scaffoldingViewModel.navManager,
                observationFromString(obs),
                showSnackbar = showSnackbar
            ))
        }

    }

}