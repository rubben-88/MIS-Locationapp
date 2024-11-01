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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import lt.ktu.locationapp.presentation.ui.displays.components.SignalTripletEditor
import lt.ktu.locationapp.presentation.ui.events.screens.ObservationEditUiEvents
import lt.ktu.locationapp.presentation.viewmodels.screens.ObservationEditViewModel

@Composable
fun ObservationEditScreen(observationEditViewModel: ObservationEditViewModel ) {

    Column(modifier = Modifier.padding(20.dp)) {

        SignalTripletEditor(observationEditViewModel.signalTripletEditorViewModel)

        Spacer(modifier = Modifier.size(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = {
                    observationEditViewModel.onEvent(ObservationEditUiEvents.Remove)
                },
                colors = androidx.compose.material3.ButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.White,
                )
            ) {
                Text("Remove")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    observationEditViewModel.onEvent(ObservationEditUiEvents.Save)
                }
            ) {
                Text("Save")
            }
        }
    }
}