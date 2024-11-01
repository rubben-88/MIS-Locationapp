package lt.ktu.locationapp.presentation.ui.events.bars

sealed class NavigationBarUiEvents {

    data class Navigate(
        val route: String = ""
    ): NavigationBarUiEvents()

}