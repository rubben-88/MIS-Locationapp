package lt.ktu.locationapp.presentation.viewmodels.screens

import androidx.lifecycle.ViewModel
import lt.ktu.locationapp.domain.models.Location
import lt.ktu.locationapp.domain.models.Observation
import lt.ktu.locationapp.domain.models.SignalTriplet
import lt.ktu.locationapp.presentation.ui.events.screens.ObservationNewUiEvents
import lt.ktu.locationapp.presentation.viewmodels.DatabaseManager
import lt.ktu.locationapp.presentation.viewmodels.NavigationManager
import lt.ktu.locationapp.presentation.viewmodels.components.LocationEditorViewModel
import lt.ktu.locationapp.presentation.viewmodels.components.SignalTripletEditorViewModel
import lt.ktu.locationapp.utils.toInteger

class ObservationNewViewModel(
    val dbManager: DatabaseManager,
    val navManager: NavigationManager,
    val showSnackbar: (String) -> Unit
) : ViewModel() {

    /* components */
    val locationEditorViewModel = LocationEditorViewModel()
    val signalTripletEditorViewModel = SignalTripletEditorViewModel()

    /* UI events */
    fun onEvent(event: ObservationNewUiEvents) {
        when(event) {
            is ObservationNewUiEvents.Save -> {
                val x = locationEditorViewModel.getX()
                val y = locationEditorViewModel.getY()
                val one = signalTripletEditorViewModel.getFirst()
                val two = signalTripletEditorViewModel.getSecond()
                val three = signalTripletEditorViewModel.getThird()
                if (toInteger(x) == null) {
                    if (x == "") {
                        showSnackbar("X can't be empty!")
                    } else {
                        showSnackbar("$x is not a integer!")
                    }
                } else if (toInteger(x) == null) {
                    if (y == "") {
                        showSnackbar("Y can't be empty!")
                    } else {
                        showSnackbar("$y is not a integer!")
                    }
                } else if (event.locationList == null) {
                    showSnackbar("A problem occurred, please try again.")
                } else if (Location(x.toInt(), y.toInt()) in event.locationList) {
                    showSnackbar("This location is already in use.")
                } else if (toInteger(one) == null) {
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
                    dbManager.createObservation(
                        Observation(
                            xy = Location(
                                x.toInt(),
                                y.toInt()
                            ),
                            ss = SignalTriplet(arrayOf(
                                one.toInt(),
                                two.toInt(),
                                three.toInt()
                            ))
                        )
                    )
                    navManager.navigateUp()
                    showSnackbar("Observation ($x,$y) was saved.")
                }
            }
        }
    }

}