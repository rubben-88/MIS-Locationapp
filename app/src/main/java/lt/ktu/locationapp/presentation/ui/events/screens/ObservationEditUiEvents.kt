package lt.ktu.locationapp.presentation.ui.events.screens

sealed class ObservationEditUiEvents {

    data object Remove: ObservationEditUiEvents()

    data object Save: ObservationEditUiEvents()

}