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
    var valueOfN = 2;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            NBackComposeTheme {
                var nBackViewModel : NBackViewModel = NBackViewModel(LocalContext.current.dataStore)
                val navController = rememberNavController()


                NavHost(navController = navController, startDestination = "GameScreen"){
                    composable("GameScreen"){
                        GameScreen(nBackViewModel = nBackViewModel, navController)
                    }
                    composable("SettingsScreen"){
                        SettingsScreen(datastore = LocalContext.current.dataStore, navController = navController)
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
@Composable
fun GameScreen(nBackViewModel: NBackViewModel, navController: NavController){
    Column(Modifier.fillMaxSize()) {
        TopAppBar(title = {Text(text = "Single-N-Back")}, backgroundColor = MaterialTheme.colors.primary, actions = {
            IconButton(onClick = { navController.navigate("SettingsScreen")}) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "Settings")
            }
        })
        TextAboveBoard(nBackViewModel = nBackViewModel)
        NBackBoard(nBackViewModel = nBackViewModel)

        ButtonsAndText(nBackViewModel = nBackViewModel)
    }
}

@Composable
fun ButtonsAndText(nBackViewModel: NBackViewModel){

    Column(verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
        val setting = 1
        if (setting == 1)
            VisualMatchButton()
        else AudioMatchButton()

        Row(horizontalArrangement = Arrangement.Center) {
            Column(verticalArrangement = Arrangement.Center) {
                Text("Score")
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Restart")
                }
                Button(onClick = { nBackViewModel.startGame() }) {
                    Text(text = "Start")
                    LaunchedEffect(nBackViewModel.isGameStarted.value) {
                        Log.i("Outside timer","tick++")
                        while(!nBackViewModel.isGameOver.value && nBackViewModel.isGameStarted.value) {
                            nBackViewModel.makeMove()
                            delay(1500)
                            Log.i("In timer","tick++")
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun TextAboveBoard(nBackViewModel: NBackViewModel){
    var valueOfN = 2
    LaunchedEffect(nBackViewModel._valueOfN){
        valueOfN = nBackViewModel._valueOfN.first()?.toInt() ?: 2
    }
    Column(verticalArrangement = Arrangement.SpaceEvenly,horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth()) {
            Text(text = "Number of events: ${nBackViewModel.currentPositionIndex.value} of 16")
            Text(text = "Selected: Visual")
        }
        Row(Modifier.fillMaxWidth()) {
            Text(text = "Time between events: $valueOfN")
            Text(text = "Value of N: 2")
        }
    }
}

@Composable
fun VisualMatchButton(){
    Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Start) {
        Button(onClick = { /*TODO*/ }) {
            Text("Visual Match")
        }
    }
}

@Composable
fun AudioMatchButton(){
    Row(horizontalArrangement = Arrangement.End) {
        Button(onClick = { /*TODO*/ }) {
            Text("Audio Match")
        }
    }
}