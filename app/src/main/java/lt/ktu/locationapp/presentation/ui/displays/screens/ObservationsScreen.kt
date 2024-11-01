package lt.ktu.locationapp.presentation.ui.displays.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.map
import lt.ktu.locationapp.presentation.ui.displays.components.ObservationCardContainer
import lt.ktu.locationapp.presentation.ui.events.screens.ObservationsUiEvents
import lt.ktu.locationapp.presentation.viewmodels.components.ObservationCardViewModel
import lt.ktu.locationapp.presentation.viewmodels.screens.ObservationsViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ObservationsScreen(observationsViewModel: ObservationsViewModel) {
    val observationsList by observationsViewModel
        .dbManager
        .getAllObservations()
        .map { it.sortedBy { it.xy } }
        .observeAsState(initial = emptyList())

    val scrollState = rememberLazyListState()

    if (observationsList.size > 0) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
            //.simpleVerticalScrollbar(scrollState), // slow + problem with loading after a while
        ) {
            items(
                count = observationsList.size,
                key = { observationsList[it].xy.toString() }
            ) {
                ObservationCardContainer(
                    observationCardViewModel = ObservationCardViewModel(
                        dbManager = observationsViewModel.dbManager,
                        navManager = observationsViewModel.navManager,
                        obs = observationsList[it]
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Nothing to display...")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            modifier = Modifier.padding(20.dp),
            onClick = { observationsViewModel.onEvent(ObservationsUiEvents.New) }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "New observation",
            )
        }
    }

}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 4.dp,
): Modifier {
    /* Dmitry @ https://stackoverflow.com/questions/66341823/jetpack-compose-scrollbars/68056586#68056586 */

    val targetAlpha = if (state.isScrollInProgress) .7f else 0f
    val duration = if (state.isScrollInProgress) 150 else 1000

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    val firstIndex by animateFloatAsState(
        targetValue = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index?.toFloat() ?: 0f,
        animationSpec = spring(stiffness = StiffnessMediumLow)
    )

    val lastIndex by animateFloatAsState(
        targetValue = state.layoutInfo.visibleItemsInfo.lastOrNull()?.index?.toFloat() ?: 0f,
        animationSpec = spring(stiffness = StiffnessMediumLow)
    )

    val color = MaterialTheme.colorScheme.tertiary

    return drawWithContent {
        drawContent()

        val itemsCount = state.layoutInfo.totalItemsCount

        if (itemsCount > 0 && alpha > 0f) {
            val scrollbarTop = firstIndex / itemsCount * size.height
            val scrollBottom = (lastIndex + 1f) / itemsCount * size.height
            val scrollbarHeight = scrollBottom - scrollbarTop
            drawRect(
                color = color,
                topLeft = Offset(size.width - width.toPx(), scrollbarTop),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}
