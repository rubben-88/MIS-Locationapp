package lt.ktu.locationapp.presentation.ui.displays.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lt.ktu.locationapp.presentation.ui.events.components.SignalTripletEditorUiEvents
import lt.ktu.locationapp.presentation.viewmodels.components.SignalTripletEditorViewModel

@Composable
fun SignalTripletEditor(signalTripletEditorViewModel: SignalTripletEditorViewModel) {
    Column {

        OutlinedTextField(
            value = signalTripletEditorViewModel.getFirst(),
            onValueChange = { signalTripletEditorViewModel.onEvent(
                SignalTripletEditorUiEvents.setFirst(it)
            ) },
            label = { Text("Signal Strength 1") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = signalTripletEditorViewModel.getSecond(),
            onValueChange = { signalTripletEditorViewModel.onEvent(
                SignalTripletEditorUiEvents.setSecond(it)
            ) },
            label = { Text("Signal Strength 2") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = signalTripletEditorViewModel.getThird(),
            onValueChange = { signalTripletEditorViewModel.onEvent(
                SignalTripletEditorUiEvents.setThird(it)
            ) },
            label = { Text("Signal Strength 3") },
            modifier = Modifier.fillMaxWidth(),
        )
    }

}