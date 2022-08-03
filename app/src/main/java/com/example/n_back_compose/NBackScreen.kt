package com.example.n_back_compose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NBackBoard(nBackViewModel: NBackViewModel){
    val currentIndex = nBackViewModel.currentPositionIndex.value;

    /*Row() {
        for (i in 0..2){
            ImageSquare(isOn = i == currentIndex)
        }
        for (i in 3..5){
            ImageSquare(isOn = i == currentIndex)

        }
        for (i in 6..8){
            ImageSquare(isOn = i == currentIndex)
        }

    }*/
    val width =

    LazyVerticalGrid(cells = GridCells.Fixed(3), modifier = Modifier.size(420.dp,420.dp)){
        items(9)  {
            index ->
                ImageSquare(isOn = index == currentIndex,130)

        }
    }
    
    /*Column(verticalArrangement = Arrangement.SpaceEvenly) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            NBackSquare(currentIndex == 0)
            NBackSquare(currentIndex == 1)
            NBackSquare(currentIndex == 2)
        }

        Row(horizontalArrangement = Arrangement.SpaceAround) {
            NBackSquare(currentIndex == 3)
            NBackSquare(currentIndex == 4)
            NBackSquare(currentIndex == 5)
        }
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            NBackSquare(currentIndex == 6)
            NBackSquare(currentIndex == 7)
            NBackSquare(currentIndex == 8)
        }
    }*/

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
            .size(size.dp,size.dp)
            .background(Color(0xFF0000FF))
            .border(1.dp, Color.Gray)
            .clip(RectangleShape)
    )
}

@Composable
fun TransparentSquare(size: Int){
    Box(
        modifier = Modifier
            .size(size.dp,size.dp)
            .background(Color.Transparent)
            .border(1.dp, Color.Gray)
            .clip(RectangleShape)
    )
}