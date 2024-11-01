package lt.ktu.locationapp.presentation.viewmodels.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import lt.ktu.locationapp.domain.models.SignalTriplet
import lt.ktu.locationapp.presentation.navigation.Routes
import lt.ktu.locationapp.presentation.ui.events.bars.AppBarUiEvents
import lt.ktu.locationapp.presentation.ui.events.screens.ProfileUiEvents
import lt.ktu.locationapp.presentation.ui.states.components.SignalTripletEditorUiState
import lt.ktu.locationapp.presentation.ui.states.screens.ProfileUiState
import lt.ktu.locationapp.presentation.viewmodels.DatabaseManager
import lt.ktu.locationapp.presentation.viewmodels.NavigationManager
import lt.ktu.locationapp.presentation.viewmodels.bars.AppBarViewModel
import lt.ktu.locationapp.presentation.viewmodels.components.SignalTripletEditorViewModel
import lt.ktu.locationapp.utils.toInteger
import lt.ktu.locationapp.utils.toMAC

class ProfileViewModel(
    val dbManager: DatabaseManager,
    val navManager: NavigationManager,
    val showSnackbar: (String) -> Unit,
    val showSnackbarSelf: (String) -> Unit
) : ViewModel() {

    /* components */
    private val _selfSignals = dbManager.getSelfSignals()
    val signalTripletEditorViewModel = SignalTripletEditorViewModel(
        SignalTripletEditorUiState(
            _selfSignals.get(0).toString(),
            _selfSignals.get(1).toString(),
            _selfSignals.get(2).toString()
        )
    )
    val appBarViewModel = AppBarViewModel(
        dbManager,
        navManager,
        showSnackbar
    )

    init {
        appBarViewModel.onNavigate(Routes.PROFILE)
        appBarViewModel.onSave = {
            val mac = uiState.mac
            val one = signalTripletEditorViewModel.getCurrentFirst()
            val two = signalTripletEditorViewModel.getCurrentSecond()
            val three = signalTripletEditorViewModel.getCurrentThird()
            if (toMAC(mac) == null) {
                if (mac == "") {
                    showSnackbarSelf("Mac can't be empty!")
                } else {
                    showSnackbarSelf("$mac is not a valid mac!")
                }
            } else if (toInteger(one) == null) {
                if (one == "") {
                    showSnackbarSelf("Signal strength 1 can't be empty!")
                } else {
                    showSnackbarSelf("$one is not a valid signal strength!")
                }
            } else if (toInteger(two) == null) {
                if (two == "") {
                    showSnackbarSelf("Signal strength 2 can't be empty!")
                } else {
                    showSnackbarSelf("$two is not a valid signal strength!")
                }
            } else if (toInteger(three) == null) {
                if (three == "") {
                    showSnackbarSelf("Signal strength 3 can't be empty!")
                } else {
                    showSnackbarSelf("$three is not a valid signal strength!")
                }
            } else {
                dbManager.setMac(mac)
                dbManager.setSelfSignals(
                    SignalTriplet(
                        arrayOf(
                            one.toInt(),
                            two.toInt(),
                            three.toInt()
                        )
                    )
                )
                appBarViewModel.onEvent(AppBarUiEvents.Close)
                showSnackbar("Profile was saved!")
            }
        }
    }


    /* UI state */
    private var uiState by mutableStateOf(ProfileUiState(
        mac = dbManager.getMac(),
    ))

    /* getters */
    fun getMac() = uiState.mac

    /* UI events */
    fun onEvent(event: ProfileUiEvents) {
        when(event) {
            is ProfileUiEvents.setMac -> uiState = uiState.copy(
                mac = event.value
            )
        }
    }

}