package lt.ktu.locationapp.presentation.ui.events.bars

import androidx.lifecycle.LifecycleOwner

sealed class AppBarUiEvents {

    data object Back: AppBarUiEvents()

    data object Close: AppBarUiEvents()

    data object OpenProfile: AppBarUiEvents()

    data object Save: AppBarUiEvents()

    data class Refresh(
        val owner: LifecycleOwner
    ): AppBarUiEvents()

}