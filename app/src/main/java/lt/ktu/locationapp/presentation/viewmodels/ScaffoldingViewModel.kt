package lt.ktu.locationapp.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import lt.ktu.locationapp.presentation.ui.states.ScaffoldingUiState

class ScaffoldingViewModel(
    val dbManager: DatabaseManager,
    val navManager: NavigationManager
): ViewModel() {

    init {
        /* TODO
        remember apparently does not work across recompositions like turning the screen
        This means that we need to use a factory and provide the viewmodel that way.
        However we use the navmanager in viewmodel because I thought a viewmodel should
        implement all logic.
        Navmanager however contains navcontroller and that is a composable so that won't work.
        This is too much work all in all for this assignment and thus we prevent the user from
        turning the app.
         */
        println("ScaffoldingViewModel init")
        navManager.attachScaffoldingViewModel(this)
    }

    /* UI state */
    private var uiState by mutableStateOf(ScaffoldingUiState())

    /* getters */
    fun getOverlay() = uiState.overlay

    /* non-UI logic */
    fun setOverlay(shown: Boolean) { uiState = ScaffoldingUiState(shown) }

}