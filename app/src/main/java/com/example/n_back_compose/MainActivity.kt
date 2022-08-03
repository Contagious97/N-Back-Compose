package com.example.n_back_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.n_back_compose.ui.theme.NBackComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var nBackViewModel : NBackViewModel = NBackViewModel()
        setContent {
            NBackComposeTheme {

                GameScreen(nBackViewModel = nBackViewModel)

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
fun GameScreen(nBackViewModel: NBackViewModel){
    Column() {
        TopAppBar(title = {Text(text = "Single-N-Back")}, backgroundColor = MaterialTheme.colors.primary)
        TextAboveBoard(nBackViewModel = nBackViewModel)
        NBackBoard(nBackViewModel = nBackViewModel)
        Button(onClick = { nBackViewModel.startGame() },modifier = Modifier.padding(10.dp)){
            LaunchedEffect(nBackViewModel.isGameStarted.value) {
                Log.i("Outside timer","tick++")
                while(!nBackViewModel.isGameOver.value && nBackViewModel.isGameStarted.value) {
                    nBackViewModel.makeMove()
                    delay(1000)
                    Log.i("In timer","tick++")
                }
            }
        }
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
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Start")
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