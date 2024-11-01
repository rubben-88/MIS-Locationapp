package lt.ktu.locationapp.presentation.viewmodels.components

import androidx.lifecycle.ViewModel
import lt.ktu.locationapp.domain.models.Observation
import lt.ktu.locationapp.presentation.navigation.Routes
import lt.ktu.locationapp.presentation.ui.events.components.ObservationCardUiEvents
import lt.ktu.locationapp.presentation.viewmodels.DatabaseManager
import lt.ktu.locationapp.presentation.viewmodels.NavigationManager

class ObservationCardViewModel(
    val dbManager: DatabaseManager,
    val navManager: NavigationManager,
    val obs: Observation
): ViewModel() {

    /* getters */
    fun getObservation() = dbManager.getObservation(obs.xy)

    /* UI events */
    fun onEvent(event: ObservationCardUiEvents) {
        when(event) {
            ObservationCardUiEvents.Click -> navManager.navigate(
                "${Routes.OBSERVATION_EDIT}/$obs"
            )
        }
    }

}