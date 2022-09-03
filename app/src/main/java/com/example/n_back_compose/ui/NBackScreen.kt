package com.example.n_back_compose

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.n_back_compose.viewmodels.NBackViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NBackBoard(nBackViewModel: NBackViewModel){
    val currentIndex = nBackViewModel.currentPositionIndex.value;

    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.size(420.dp,420.dp)){
        items(9)  {
            index ->
                ImageSquare(isOn = index == currentIndex,130)

        }
    }

}


@Composable
fun NBackSquare(isOn: Boolean) {
    Box(modifier = Modifier.padding(10.dp)) {
        Canvas(Modifier.fillMaxSize()){
            if (isOn){
                drawRect(
                    color = Color.Magenta,
                )
            } else
                drawRect(
                    color = Color.Magenta,
                    style = Stroke(5.dp.toPx())
                )
        }
    }
}

@Composable
fun ImageSquare(isOn: Boolean, size: Int){
    /*val filledImage = painterResource(id = R.drawable.img_blue)
    val unFilledImage = painterResource(id = R.drawable.layout_border_bkg)*/
    
    if (isOn){
        FilledSquare(size)
    } else TransparentSquare(size)
}

@Composable
fun FilledSquare(size: Int){
    Box(
        modifier = Modifier
            .size(size.dp, size.dp)
            .background(Color(0xFF0000FF))
            .border(1.dp, Color.Gray)
            .clip(RectangleShape)
    )
}

@Composable
fun TransparentSquare(size: Int){
    Box(
        modifier = Modifier
            .size(size.dp, size.dp)
            .background(Color.Transparent)
            .border(1.dp, Color.Gray)
            .clip(RectangleShape)
    )
}

@Composable
fun GameScreen(nBackViewModel: NBackViewModel, navController: NavController){
    val configuration = LocalConfiguration.current;
    when(configuration.layoutDirection){
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(Modifier.fillMaxSize()) {
                TopAppBar(title = { Text(text = "Single-N-Back") }, backgroundColor = MaterialTheme.colors.primary, actions = {
                    IconButton(onClick = { navController.navigate("SettingsScreen")}) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Settings")
                    }
                })
                NBackBoard(nBackViewModel = nBackViewModel)
                TextAboveBoard(nBackViewModel = nBackViewModel)
                ButtonsAndText(nBackViewModel = nBackViewModel)
            }
        } else ->{
        Column(Modifier.fillMaxSize()) {
            TopAppBar(title = { Text(text = "Single-N-Back") }, backgroundColor = MaterialTheme.colors.primary, actions = {
                IconButton(onClick = { navController.navigate("SettingsScreen")}) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Settings")
                }
            })
            TextAboveBoard(nBackViewModel = nBackViewModel)
            NBackBoard(nBackViewModel = nBackViewModel)

            ButtonsAndText(nBackViewModel = nBackViewModel)
        }
        }
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
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
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
    /*val valueOfN = nBackViewModel.valueOfN.value
    val timeBetweenEvents = nBackViewModel.timeBetweenEvents.value
    val selectedStimuli = nBackViewModel.selectedStimuli.value*/

    val v = nBackViewModel._valueOfN.collectAsState(initial = "2")
    val t = nBackViewModel._timeBetweenEvents.collectAsState(initial = "1000")
    val s = nBackViewModel._selectedStimuli.collectAsState(initial = "Visual")
    Column(verticalArrangement = Arrangement.SpaceEvenly,horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth()) {
            Text(text = "Number of events: ${nBackViewModel.currentPositionIndex.value} of 16")
            Text(text = "Selected: ${s.value}")
        }
        Row(Modifier.fillMaxWidth()) {
            Text(text = "Time between events: ${t.value.toString()}")
            Text(text = "Value of N: ${v.value}")
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