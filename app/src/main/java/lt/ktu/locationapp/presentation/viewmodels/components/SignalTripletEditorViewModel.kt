package lt.ktu.locationapp.presentation.viewmodels.components

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import lt.ktu.locationapp.presentation.ui.events.components.SignalTripletEditorUiEvents
import lt.ktu.locationapp.presentation.ui.states.components.SignalTripletEditorUiState

class SignalTripletEditorViewModel(
    uiState2: SignalTripletEditorUiState? = null
): ViewModel() {

    /* UI state */
    private var uiState = mutableStateOf(SignalTripletEditorUiState())

    init {
        if (uiState2 != null) { uiState.value = uiState2 }
    }

    /* getters */
    fun getFirst()  = uiState.value.first
    fun getSecond() = uiState.value.second
    fun getThird()  = uiState.value.third

    /* UI events */
    fun onEvent(event: SignalTripletEditorUiEvents) {
        when(event) {
            is SignalTripletEditorUiEvents.setFirst  -> uiState.value = uiState.value.copy(first = event.value)
            is SignalTripletEditorUiEvents.setSecond -> uiState.value = uiState.value.copy(second = event.value)
            is SignalTripletEditorUiEvents.setThird  -> uiState.value = uiState.value.copy(third = event.value)
        }
    }

    /* non-UI logic */
    fun getCurrentFirst()  = uiState.value.first
    fun getCurrentSecond() = uiState.value.second
    fun getCurrentThird()  = uiState.value.third

}