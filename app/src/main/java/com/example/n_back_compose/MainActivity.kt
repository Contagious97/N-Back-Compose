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
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.n_back_compose.ui.theme.NBackComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var nBackViewModel : NBackViewModel = NBackViewModel()
        setContent {

            NBackComposeTheme {
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
    var nBackViewModel : NBackViewModel = NBackViewModel()
    NBackComposeTheme {
        Column() {
            NBackBoard(nBackViewModel = nBackViewModel)
            Button(onClick = { nBackViewModel.startGame() }){}
        }

    }
}
@Composable
fun GameScreen(nBackViewModel: NBackViewModel, navController: NavController){
    Column() {
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

    Column(Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
        val setting = 1
        if (setting == 1)
            VisualMatchButton()
        else AudioMatchButton()

        Row(Modifier.padding(10.dp),horizontalArrangement = Arrangement.Center) {
            Column(modifier = Modifier.padding(10.dp),verticalArrangement = Arrangement.Center) {
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
    Column(verticalArrangement = Arrangement.SpaceEvenly,horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth()) {
            Text(text = "Number of events: ${nBackViewModel.currentPositionIndex.value} of 16")
            Text(text = "Selected: Visual")
        }
        Row(Modifier.fillMaxWidth()) {
            Text(text = "Time between events: $")
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