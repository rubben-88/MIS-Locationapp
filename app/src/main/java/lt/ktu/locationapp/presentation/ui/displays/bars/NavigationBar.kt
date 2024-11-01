package lt.ktu.locationapp.presentation.ui.displays.bars

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import lt.ktu.locationapp.presentation.navigation.topLevelRoutes
import lt.ktu.locationapp.presentation.ui.events.bars.NavigationBarUiEvents
import lt.ktu.locationapp.presentation.viewmodels.bars.NavigationBarViewModel

@Composable
fun NavigationBar(navBarViewModel: NavigationBarViewModel) {

    AnimatedVisibility(
        visible = navBarViewModel.getShown(),
        enter = fadeIn(),
        exit = fadeOut(animationSpec = tween(durationMillis = 700))
    ) {

        androidx.compose.material3.NavigationBar(
            modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
            val componentHeight = layoutCoordinates.size.height.dp
            Log.d("MyComponent", "Height: $componentHeight")
        }
        ) {
            val navBackStackEntry by navBarViewModel.navManager.navController.currentBackStackEntryAsState()

            topLevelRoutes.forEach {
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(
                                id =
                                if (navBackStackEntry?.destination?.route == it.route)
                                    it.selectedIcon
                                else
                                    it.unselectedIcon
                            ),
                            "",
                            tint = if (navBackStackEntry?.destination?.route == it.route)
                                Color.DarkGray
                            else
                                Color.Gray
                        )
                    },
                    //icon = {
                    //    painterResource(
                    //        id =  if (navBackStackEntry?.destination?.route == it.route)
                    //            it.selectedIcon
                    //        else
                    //            it.unselectedIcon,
                    //    )
                    //},
                    selected = navBackStackEntry?.destination?.route == it.route,
                    onClick = { navBarViewModel.onEvent(NavigationBarUiEvents.Navigate(it.route)) },
                    label = {
                        Text(
                            text = it.title,
                            color = if (navBackStackEntry?.destination?.route == it.route)
                                Color.DarkGray
                            else
                                Color.Gray
                        )
                    },
                )
            }

        }
    }


}