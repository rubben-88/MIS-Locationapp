package lt.ktu.locationapp.presentation.navigation

import lt.ktu.locationapp.R

const val startingTopLevelRoute = Routes.MAP

object Routes {
    const val MAP               = "MAP"
    const val PROFILE           = "PROFILE"
    const val OBSERVATIONS      = "OBSERVATIONS"
    const val OBSERVATION_NEW   = "OBSERVATION_NEW"
    const val OBSERVATION_EDIT  = "OBSERVATION_EDIT"
}

val topLevelRoutes = listOf(
     TopLevelRoute(
         route = Routes.MAP,
         title = "Map",
         selectedIcon = R.drawable.map,
         unselectedIcon = R.drawable.map
    ),
    TopLevelRoute(
        route = Routes.OBSERVATIONS,
        title = "Signal Strengths",
        selectedIcon = R.drawable.sensors,
        unselectedIcon = R.drawable.sensors,
    )
)

fun baseRoute(route: String) = route.split("/")[0]

