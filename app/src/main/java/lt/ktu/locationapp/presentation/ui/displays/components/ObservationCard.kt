package lt.ktu.locationapp.presentation.ui.displays.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lt.ktu.locationapp.presentation.ui.events.components.ObservationCardUiEvents
import lt.ktu.locationapp.presentation.viewmodels.components.ObservationCardViewModel

@Composable
fun ObservationCard(
    observationCardViewModel: ObservationCardViewModel
) {

    val obs by observationCardViewModel.getObservation().observeAsState()

    if (obs == null) {
        Text("loading...")
    } else {
        Card(
            modifier = Modifier.width(230.dp),
            onClick = { observationCardViewModel.onEvent(ObservationCardUiEvents.Click) },
        ) {
            Column(modifier = Modifier.padding(5.dp)) {

                Text(
                    text = obs!!.xy.toString(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 2.dp),
                    thickness = 1.dp,
                    color = Color.LightGray,
                )

                Row {
                    Text(text = "one: ${obs!!.ss.get(0)}")

                    Spacer(modifier = Modifier.size(20.dp))

                    Text(text = "two: ${obs!!.ss.get(1)}")

                    Spacer(modifier = Modifier.size(20.dp))

                    Text(text = "three: ${obs!!.ss.get(2)}")
                }

            }
        }
    }
}

@Composable
fun ObservationCardContainer(
    observationCardViewModel: ObservationCardViewModel,
    modifier: Modifier,
    contentAlignment: Alignment,
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment,
    ) {
        ObservationCard(
            observationCardViewModel = observationCardViewModel
        )
    }
}