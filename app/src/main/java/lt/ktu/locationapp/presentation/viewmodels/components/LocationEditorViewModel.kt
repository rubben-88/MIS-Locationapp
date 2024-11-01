package lt.ktu.locationapp.presentation.viewmodels.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import lt.ktu.locationapp.presentation.ui.events.components.LocationEditorUiEvents
import lt.ktu.locationapp.presentation.ui.states.components.LocationEditorUiState

class LocationEditorViewModel: ViewModel() {

    /* UI state */
    private var uiState by mutableStateOf(LocationEditorUiState())

    /* getters */
    fun getX() = uiState.x
    fun getY() = uiState.y

    /* UI events */
    fun onEvent(event: LocationEditorUiEvents) {
        when(event) {
            is LocationEditorUiEvents.setX -> uiState = uiState.copy(x = event.value)
            is LocationEditorUiEvents.setY -> uiState = uiState.copy(y = event.value)
        }
    }

    /* non-UI logic */
    fun getCurrentX() = uiState.x
    fun getCurrentY() = uiState.y

}