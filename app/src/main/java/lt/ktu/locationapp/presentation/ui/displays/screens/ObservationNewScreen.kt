package lt.ktu.locationapp.presentation.ui.displays.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.map
import lt.ktu.locationapp.domain.models.Location
import lt.ktu.locationapp.presentation.ui.displays.components.LocationEditor
import lt.ktu.locationapp.presentation.ui.displays.components.SignalTripletEditor
import lt.ktu.locationapp.presentation.ui.events.screens.ObservationNewUiEvents
import lt.ktu.locationapp.presentation.viewmodels.screens.ObservationNewViewModel

@Composable
fun ObservationNewScreen(observationNewViewModel: ObservationNewViewModel) {

    val locationList: List<Location>? by observationNewViewModel
        .dbManager
        .getAllObservations()
        .map { it.map { it.xy } }
        .observeAsState(initial = null)

    Column(modifier = Modifier.padding(20.dp)) {

        LocationEditor(observationNewViewModel.locationEditorViewModel)

        Spacer(modifier = Modifier.size(20.dp))

        SignalTripletEditor(observationNewViewModel.signalTripletEditorViewModel)

        Spacer(modifier = Modifier.size(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    observationNewViewModel.onEvent(
                        ObservationNewUiEvents.Save(
                            locationList
                        )
                    )
                }
            ) {
                Text("Save")
            }
        }
    }
}