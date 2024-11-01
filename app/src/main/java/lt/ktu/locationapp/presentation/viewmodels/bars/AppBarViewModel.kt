package lt.ktu.locationapp.presentation.viewmodels.bars

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import lt.ktu.locationapp.domain.models.observationFromString
import lt.ktu.locationapp.presentation.navigation.Routes
import lt.ktu.locationapp.presentation.ui.events.bars.AppBarUiEvents
import lt.ktu.locationapp.presentation.ui.states.bars.AppBarAction
import lt.ktu.locationapp.presentation.ui.states.bars.AppBarNavigation
import lt.ktu.locationapp.presentation.ui.states.bars.AppBarUiState
import lt.ktu.locationapp.presentation.viewmodels.DatabaseManager
import lt.ktu.locationapp.presentation.viewmodels.NavigationManager

class AppBarViewModel(
    val dbManager: DatabaseManager,
    val navManager: NavigationManager,
    val showSnackbar: (String) -> Unit,
): ViewModel() {

    var onSave = {} // override when making viewModel that needs save

    var scope: CoroutineScope? = null
    var closeEffect = suspend {}

    init {
        navManager.attachAppBarViewModel(this)
    }

    /* UI state */
    private var uiState by mutableStateOf(AppBarUiState())

    /* getters */
    fun getNavigation()         = uiState.navigation
    fun getAction1()            = uiState.action1
    fun getAction2()            = uiState.action2
    fun getAction1Disabled()    = uiState.action1Disabled
    fun getAction2Disabled()    = uiState.action2Disabled
    fun getTitle()              = uiState.title
    fun getBackGroundColor()    = uiState.backgroundColor

    /* UI events */
    fun onEvent(event: AppBarUiEvents) {
        when(event) {
            AppBarUiEvents.Back         -> navManager.navigateUp()
            AppBarUiEvents.Close        -> {
                if (scope != null) {
                    scope!!.launch {
                        closeEffect()
                        closeEffect = {}
                        scope = null
                        navManager.navigateUp()
                    }
                } else {
                    navManager.navigateUp()
                }
            }
            AppBarUiEvents.OpenProfile  -> navManager.navigate(Routes.PROFILE)
            AppBarUiEvents.Save         -> onSave()
            is AppBarUiEvents.Refresh   -> {
                uiState = uiState.copy(action1Disabled = true)
                viewModelScope.launch {
                    dbManager.reloadDatabase(event.owner)
                    showSnackbar("Database was reloaded!")
                    uiState = uiState.copy(action1Disabled = false)
                }
            }
        }
    }

    /* non-UI logic */
    fun onNavigate(route: String) {
        val routeBase = route.split("/")[0]
        when(routeBase) {
            Routes.MAP -> uiState = AppBarUiState(
                navigation          = AppBarNavigation.NONE,
                title               = "Map",
                action1             = AppBarAction.REFRESH,
                action2             = AppBarAction.PROFILE,
                backgroundColor     = Color.LightGray
            )
            Routes.PROFILE -> uiState = AppBarUiState(
                navigation          = AppBarNavigation.CLOSE,
                title               = "Profile",
                action1             = AppBarAction.NONE,
                action2             = AppBarAction.SAVE,
                backgroundColor     = Color.White
            )
            Routes.OBSERVATIONS -> uiState = AppBarUiState(
                navigation          = AppBarNavigation.NONE,
                title               = "Signal Strengths",
                action1             = AppBarAction.REFRESH,
                action2             = AppBarAction.PROFILE,
                backgroundColor     = Color.LightGray
            )
            Routes.OBSERVATION_EDIT -> uiState = AppBarUiState(
                navigation          = AppBarNavigation.BACK,
                title               = "Edit ${observationFromString(
                    route.split("/")[1]
                ).xy}",
                action1             = AppBarAction.NONE,
                action2             = AppBarAction.NONE,
                backgroundColor     = Color.LightGray
            )
            Routes.OBSERVATION_NEW -> uiState = AppBarUiState(
                navigation          = AppBarNavigation.BACK,
                title               = "New Observation",
                action1             = AppBarAction.NONE,
                action2             = AppBarAction.NONE,
                backgroundColor     = Color.LightGray
            )
        }
    }

}