package lt.ktu.locationapp.presentation.viewmodels

import androidx.navigation.NavHostController
import lt.ktu.locationapp.presentation.navigation.Routes
import lt.ktu.locationapp.presentation.viewmodels.bars.AppBarViewModel
import lt.ktu.locationapp.presentation.viewmodels.bars.NavigationBarViewModel

class NavigationManager(
    val navController: NavHostController // rememberNavController() in composable
) {

    /* init */
    lateinit var appBarViewModel: AppBarViewModel
    lateinit var navBarViewModel: NavigationBarViewModel
    lateinit var scaffoldingViewModel: ScaffoldingViewModel
    fun attachAppBarViewModel(appBarViewModel: AppBarViewModel) {
        if (! ::appBarViewModel.isInitialized) { this.appBarViewModel = appBarViewModel }
    }
    fun attachNavigationBarViewModel(navBarViewModel: NavigationBarViewModel) {
        if (! ::navBarViewModel.isInitialized) { this.navBarViewModel = navBarViewModel }
    }
    fun attachScaffoldingViewModel(scaffoldingViewModel: ScaffoldingViewModel) {
        if (! ::scaffoldingViewModel.isInitialized) { this.scaffoldingViewModel = scaffoldingViewModel }
    }


    /* logic */
    fun navigate(route: String) {
        if (route == Routes.PROFILE) { // special effect
            scaffoldingViewModel.setOverlay(true)
            return
        }
        appBarViewModel.onNavigate(route)
        navBarViewModel.onNavigate(route)
        navController.navigate(route)
    }

    fun navigateUp() {
        if (scaffoldingViewModel.getOverlay()) { // special effect
            // TODO: getting close-effect or scope here does not work for some reason
            scaffoldingViewModel.setOverlay(false)
            return
        }

        val previous = navController.previousBackStackEntry?.destination?.route!!
        appBarViewModel.onNavigate(previous)
        navBarViewModel.onNavigate(previous)
        navController.navigateUp()

    }

}