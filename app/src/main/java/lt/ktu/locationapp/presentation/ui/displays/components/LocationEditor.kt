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
import lt.ktu.locationapp.presentation.ui.events.components.LocationEditorUiEvents
import lt.ktu.locationapp.presentation.viewmodels.components.LocationEditorViewModel

@Composable
fun LocationEditor(locationEditorViewModel: LocationEditorViewModel) {
    Column {

        OutlinedTextField(
            value = locationEditorViewModel.getX(),
            onValueChange = { locationEditorViewModel.onEvent(LocationEditorUiEvents.setX(it)) },
            label = { Text("X") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.size(20.dp))

        OutlinedTextField(
            value = locationEditorViewModel.getY(),
            onValueChange = { locationEditorViewModel.onEvent(LocationEditorUiEvents.setY(it)) },
            label = { Text("Y") },
            modifier = Modifier.fillMaxWidth(),
        )

    }
}