package lt.ktu.locationapp.presentation.ui.displays.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.times
import kotlinx.coroutines.delay
import lt.ktu.locationapp.domain.models.Location
import lt.ktu.locationapp.presentation.viewmodels.DatabaseManager
import lt.ktu.locationapp.utils.bestFittingLocation
import me.saket.telephoto.zoomable.ZoomSpec
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable
import kotlin.math.abs
import kotlin.math.sign

@Composable
fun MapScreen(dbManager: DatabaseManager) {

    val observationsList by dbManager
        .getAllObservations()
        .observeAsState(initial = emptyList())

    val selfSignals = dbManager.getSelfSignals()

    val myPosition: Location = bestFittingLocation(observationsList, selfSignals)

    val squareList: MutableList<Pair<Location, Color>> = observationsList.map { Pair(it.xy, Color.LightGray) }.toMutableList()
    squareList.add(Pair(Location(0,0), Color.Gray))
    squareList.add(Pair(myPosition, Color.Red))

    var x_max = Int.MIN_VALUE
    var x_min = Int.MAX_VALUE
    var y_max = Int.MIN_VALUE
    var y_min = Int.MAX_VALUE

    observationsList.forEach {
        if (x_max < it.xy.x) { x_max = it.xy.x }
        if (x_min > it.xy.x) { x_min = it.xy.x }
        if (y_max < it.xy.y) { y_max = it.xy.y }
        if (y_min > it.xy.y) { y_min = it.xy.y }
    }

    val dist_x = abs(x_max - x_min)
    val dist_y = abs(y_max - y_min)

    var amntHor = dist_x // From database
    var amntVer = dist_y // From database
    if (sign(x_min.toDouble()) != sign(x_max.toDouble())) {amntHor++} // account for 0 position
    if (sign(y_min.toDouble()) != sign(y_max.toDouble())) {amntVer++} // account for 0 position


    val maxZoom = 5f // Could be calculated, but I'm lazy
    val initZoom = 1f // No zoom yet, but now I know it is possible (centroid probably as well than)

    // zoomState needs rememberZoomableState, so can't be in viewModel
    val zoomState = rememberZoomableState(zoomSpec = ZoomSpec(maxZoomFactor = maxZoom))
    // these variables are not really something that should change, but are initialized after global positioning
    var sqrSizeInt by remember { mutableIntStateOf(0) }
    var scrnWidthInt by remember { mutableIntStateOf(0) }
    var scrnHeightInt by remember { mutableIntStateOf(0) }

    val sqrSize = sqrSizeInt.pxToDp()
    val scrnWidth = scrnWidthInt.pxToDp()
    val scrnHeight = scrnHeightInt.pxToDp()

    val whiteSpaceWidth = scrnWidth - amntHor * sqrSize
    val whiteSpaceHeight = scrnHeight - amntVer * sqrSize

    LaunchedEffect(key1 = "key1") { // Composes' equivalent of onCreate
        delay(100) // Not good practice, but nothing else works atm.
        zoomState.zoomBy(initZoom)
    }

    if (observationsList.size > 0) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    scrnWidthInt = coordinates.size.width
                    scrnHeightInt = coordinates.size.height

                    val wouldBeSqrWidth = scrnWidthInt / amntHor
                    val wouldBeSqrHeight = scrnHeightInt / amntVer

                    sqrSizeInt = if (wouldBeSqrWidth > wouldBeSqrHeight) {
                        wouldBeSqrHeight
                    } else {
                        wouldBeSqrWidth
                    }
                }
                .zoomable(zoomState)
        ) {

            // draw special squares
            for (squarePair in squareList) {
                var x = squarePair.first.x
                var y = squarePair.first.y
                if (x_min < 0) {
                    x = x - x_min
                }
                if (y_min < 0) {
                    y = (y_max - y_min) - (y - y_min)
                }
                drawRect(
                    color = squarePair.second,
                    topLeft = Offset(
                        (whiteSpaceWidth / 2).toPx() + x * sqrSize.toPx(),
                        (whiteSpaceHeight / 2).toPx() + y * sqrSize.toPx()
                    ),
                    size = androidx.compose.ui.geometry.Size(sqrSize.toPx(), sqrSize.toPx())
                )
            }

            // Draw vertical lines
            for (i in 0..amntHor) {
                drawLine(
                    color = Color.Black,
                    start = Offset(
                        (whiteSpaceWidth / 2).toPx() + i * sqrSize.toPx(),
                        (whiteSpaceHeight / 2).toPx() + 0f
                    ),
                    end = Offset(
                        (whiteSpaceWidth / 2).toPx() + i * sqrSize.toPx(),
                        -(whiteSpaceHeight / 2).toPx() + scrnHeight.toPx()
                    ),
                )
            }

            // Draw horizontal lines
            for (j in 0..amntVer) {
                drawLine(
                    color = Color.Black,
                    start = Offset(
                        (whiteSpaceWidth / 2).toPx() + 0f,
                        (whiteSpaceHeight / 2).toPx() + j * sqrSize.toPx()
                    ),
                    end = Offset(
                        -(whiteSpaceWidth / 2).toPx() + scrnWidth.toPx(),
                        (whiteSpaceHeight / 2).toPx() + j * sqrSize.toPx()
                    ),
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
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) {
    this@pxToDp.toDp()
}