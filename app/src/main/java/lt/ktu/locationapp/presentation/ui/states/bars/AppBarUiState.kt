package lt.ktu.locationapp.presentation.ui.states.bars

import androidx.compose.ui.graphics.Color

data class AppBarUiState (
    val navigation      : AppBarNavigation  = AppBarNavigation.NONE,
    val title           : String            = "",
    val action1         : AppBarAction      = AppBarAction.NONE,
    val action1Disabled : Boolean           = false,
    val action2         : AppBarAction      = AppBarAction.NONE,
    val action2Disabled : Boolean           = false,
    val backgroundColor : Color             = Color.White
)

enum class AppBarNavigation {
    NONE, BACK, CLOSE
}

enum class AppBarAction {
    NONE, REFRESH, PROFILE, SAVE
}