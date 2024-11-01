package lt.ktu.locationapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import lt.ktu.locationapp.di.DependencyProvider
import lt.ktu.locationapp.presentation.ui.displays.Scaffolding
import lt.ktu.locationapp.presentation.viewmodels.DatabaseManager
import lt.ktu.locationapp.presentation.viewmodels.NavigationManager
import lt.ktu.locationapp.presentation.viewmodels.ScaffoldingViewModel

class MainActivity : ComponentActivity() {

    private val dbManager by viewModels<DatabaseManager> {
        DependencyProvider.provideDatabaseViewModelFactory(applicationContext)
    }

    private var navigateUp = {}

    override fun onBackPressed() { // ignore error
        navigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            //var loading by remember { mutableStateOf(true) }
            //
            //val owner = this
            //LaunchedEffect(key1 = "") {
            //    dbManager.reloadDatabase(owner)
            //    loading = false
            //}

            //if (loading) {
            //    Loader()
            //} else {
                val navController = rememberNavController()
                val navManager = remember { NavigationManager(navController) }

                navigateUp = { navManager.navigateUp() }

                val scaffoldingViewModel = remember { ScaffoldingViewModel(
                    dbManager,
                    navManager
                ) }

                Scaffolding(
                    scaffoldingViewModel
                )
            //}



        }
    }
}

@Composable
fun Loader() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Loading...")
    }
}
