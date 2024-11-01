package lt.ktu.locationapp.presentation.ui.events.components

sealed class LocationEditorUiEvents {

    data class setX(val value: String): LocationEditorUiEvents()

    data class setY(val value: String): LocationEditorUiEvents()

}