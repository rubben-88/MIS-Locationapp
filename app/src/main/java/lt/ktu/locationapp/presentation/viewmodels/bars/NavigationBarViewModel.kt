package lt.ktu.locationapp.presentation.viewmodels.bars

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import lt.ktu.locationapp.presentation.navigation.Routes
import lt.ktu.locationapp.presentation.ui.events.bars.NavigationBarUiEvents
import lt.ktu.locationapp.presentation.ui.states.bars.NavigationBarUiState
import lt.ktu.locationapp.presentation.viewmodels.NavigationManager

class NavigationBarViewModel(val navManager: NavigationManager): ViewModel() {

    init {
        navManager.attachNavigationBarViewModel(this)
    }

    /* UI state */
    private var uiState by mutableStateOf(NavigationBarUiState())

    /* getters */
    fun getShown() = uiState.shown

    /* UI events */
    fun onEvent(event: NavigationBarUiEvents) {
        when(event) {
            is NavigationBarUiEvents.Navigate -> navManager.navigate(event.route)
        }
    }

    /* non-UI logic */
    fun onNavigate(route: String) {
        val routeBase = route.split("/")[0]
        when(routeBase) {
            Routes.MAP              -> uiState = NavigationBarUiState(shown = true)
            Routes.PROFILE          -> uiState = NavigationBarUiState(shown = true)
            Routes.OBSERVATIONS     -> uiState = NavigationBarUiState(shown = true)
            Routes.OBSERVATION_EDIT -> uiState = NavigationBarUiState(shown = false)
            Routes.OBSERVATION_NEW  -> uiState = NavigationBarUiState(shown = false)

        }
    }

}