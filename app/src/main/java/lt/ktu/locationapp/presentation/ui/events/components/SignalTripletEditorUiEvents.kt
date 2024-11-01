package lt.ktu.locationapp.presentation.ui.events.components

sealed class SignalTripletEditorUiEvents {

    data class setFirst(val value: String): SignalTripletEditorUiEvents()

    data class setSecond(val value: String): SignalTripletEditorUiEvents()

    data class setThird(val value: String): SignalTripletEditorUiEvents()

}