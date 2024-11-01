package lt.ktu.locationapp.presentation.viewmodels.screens

import androidx.lifecycle.ViewModel
import lt.ktu.locationapp.presentation.navigation.Routes
import lt.ktu.locationapp.presentation.ui.events.screens.ObservationsUiEvents
import lt.ktu.locationapp.presentation.viewmodels.DatabaseManager
import lt.ktu.locationapp.presentation.viewmodels.NavigationManager

class ObservationsViewModel(
    val dbManager: DatabaseManager,
    val navManager: NavigationManager
) : ViewModel() {

    /* UI events */
    fun onEvent(event: ObservationsUiEvents) {
        when(event){
            ObservationsUiEvents.New -> navManager.navigate(Routes.OBSERVATION_NEW)
        }
    }

}