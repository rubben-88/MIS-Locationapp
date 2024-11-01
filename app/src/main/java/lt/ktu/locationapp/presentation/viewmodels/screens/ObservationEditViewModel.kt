package lt.ktu.locationapp.presentation.viewmodels.screens

import androidx.lifecycle.ViewModel
import lt.ktu.locationapp.domain.models.Observation
import lt.ktu.locationapp.domain.models.SignalTriplet
import lt.ktu.locationapp.presentation.ui.events.screens.ObservationEditUiEvents
import lt.ktu.locationapp.presentation.ui.states.components.SignalTripletEditorUiState
import lt.ktu.locationapp.presentation.viewmodels.DatabaseManager
import lt.ktu.locationapp.presentation.viewmodels.NavigationManager
import lt.ktu.locationapp.presentation.viewmodels.components.SignalTripletEditorViewModel
import lt.ktu.locationapp.utils.toInteger

class ObservationEditViewModel(
    val dbManager: DatabaseManager,
    val navManager: NavigationManager,
    val current: Observation,
    val showSnackbar: (String) -> Unit
) : ViewModel() {

    /* components */
    val signalTripletEditorViewModel = SignalTripletEditorViewModel(
        SignalTripletEditorUiState(
            current.ss.get(0).toString(),
            current.ss.get(1).toString(),
            current.ss.get(2).toString()
        )
    )

    /* UI events */
    fun onEvent(event: ObservationEditUiEvents) {
        when(event) {
            ObservationEditUiEvents.Save -> {
                val one = signalTripletEditorViewModel.getCurrentFirst()
                val two = signalTripletEditorViewModel.getCurrentSecond()
                val three = signalTripletEditorViewModel.getCurrentThird()
                if (toInteger(one) == null) {
                    if (one == "") {
                        showSnackbar("Signal strength 1 can't be empty!")
                    } else {
                        showSnackbar("$one is not a valid signal strength!")
                    }
                } else if (toInteger(two) == null) {
                    if (two == "") {
                        showSnackbar("Signal strength 2 can't be empty!")
                    } else {
                        showSnackbar("$two is not a valid signal strength!")
                    }
                } else if (toInteger(three) == null) {
                    if (three == "") {
                        showSnackbar("Signal strength 3 can't be empty!")
                    } else {
                        showSnackbar("$three is not a valid signal strength!")
                    }
                } else {
                    dbManager.updateObservation(
                        Observation(
                            xy = current.xy,
                            ss = SignalTriplet(
                                arrayOf(
                                    one.toInt(),
                                    two.toInt(),
                                    three.toInt()
                                )
                            )
                        )
                    )
                    navManager.navigateUp()
                    showSnackbar("Observation ${current.xy} was saved.")
                }
            }
            ObservationEditUiEvents.Remove -> {
                dbManager.deleteObservation(current.xy)
                navManager.navigateUp()
                showSnackbar("Observation ${current.xy} was deleted.")
            }
        }
    }

}