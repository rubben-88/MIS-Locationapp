package lt.ktu.locationapp.presentation.ui.events.screens

sealed class ProfileUiEvents {

    data class setMac(val value: String): ProfileUiEvents()

}