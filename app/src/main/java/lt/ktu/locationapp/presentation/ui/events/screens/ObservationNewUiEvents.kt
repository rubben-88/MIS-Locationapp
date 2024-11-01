package lt.ktu.locationapp.presentation.ui.events.screens

import lt.ktu.locationapp.domain.models.Location

sealed class ObservationNewUiEvents {

    data class Save(val locationList: List<Location>?): ObservationNewUiEvents()

}