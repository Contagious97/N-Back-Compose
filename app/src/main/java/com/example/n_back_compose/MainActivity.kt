package com.example.n_back_compose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.n_back_compose.ui.theme.NBackComposeTheme
import com.example.n_back_compose.viewmodels.NBackViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first


class MainActivity : ComponentActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var nBackViewModel : NBackViewModel
    var valueOfN = 2;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            NBackComposeTheme {
                //val repository : Repository = Repository(LocalContext.current)
                nBackViewModel = NBackViewModel(LocalContext.current.dataStore)
                //nBackViewModel.getSettings()
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "GameScreen"){
                    composable("SettingsScreen"){
                        SettingsScreen(datastore = LocalContext.current.dataStore, navController = navController)
                    }
                    composable("GameScreen"){
                        GameScreen(nBackViewModel = nBackViewModel, navController)
                        nBackViewModel.getSettings()
                    }
                }

            }
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    /*var nBackViewModel : NBackViewModel = NBackViewModel(LocalContext.current.dataStore)
    NBackComposeTheme {
        Column() {
            NBackBoard(nBackViewModel = nBackViewModel)
            Button(onClick = { nBackViewModel.startGame() }){}
        }

    }*/
}

