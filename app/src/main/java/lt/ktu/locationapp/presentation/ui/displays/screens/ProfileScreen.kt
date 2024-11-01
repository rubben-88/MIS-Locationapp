package lt.ktu.locationapp.presentation.ui.displays.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.SnackbarHost
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import lt.ktu.locationapp.presentation.ui.displays.SnackbarDemoAppState
import lt.ktu.locationapp.presentation.ui.displays.bars.AppBar
import lt.ktu.locationapp.presentation.ui.displays.components.SignalTripletEditor
import lt.ktu.locationapp.presentation.ui.events.screens.ProfileUiEvents
import lt.ktu.locationapp.presentation.viewmodels.screens.ProfileViewModel
import kotlin.math.roundToInt

@Composable
fun MovingProfileScreen(
    profileViewModel: ProfileViewModel,
    snackBarState: SnackbarDemoAppState
) {

    val scope = rememberCoroutineScope()

    val screenHeightPx = LocalView.current.height.toFloat()
    val offsetY = remember { Animatable(screenHeightPx) }

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 500),
        )
        profileViewModel.appBarViewModel.scope = scope
        println("scope set: $scope")
        profileViewModel.appBarViewModel.closeEffect = {
            offsetY.animateTo(
                targetValue = screenHeightPx,
                animationSpec = tween(durationMillis = 500),
            )
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(0, offsetY.value.roundToInt()) },
        contentAlignment = Alignment.Center,
    ) {
        ProfileScreen(
            profileViewModel = profileViewModel,
            snackBarState = snackBarState
        )
    }
}

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    snackBarState: SnackbarDemoAppState
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarState.scaffoldState.snackbarHostState)
        },
        topBar = {
            AppBar(
                appBarViewModel = profileViewModel.appBarViewModel
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(20.dp)
            ) {
                OutlinedTextField(
                    value = profileViewModel.getMac(),
                    onValueChange = { profileViewModel.onEvent(ProfileUiEvents.setMac(it)) },
                    label = { Text("MAC") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.size(40.dp))

                SignalTripletEditor(profileViewModel.signalTripletEditorViewModel)
            }
        }
    )
}