package com.example.n_back_compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NBackViewModel : ViewModel(){

    private var _gameLogic : GameLogic = GameLogic.instance!!

    var currentPositionIndex : MutableState<Int> = mutableStateOf(_gameLogic.position)

    var isGameOver : MutableState<Boolean> = mutableStateOf(_gameLogic.gameOver())
    var isGameStarted : MutableState<Boolean> = mutableStateOf(_gameLogic.isGameStarted)

    fun startGame() {
        _gameLogic.reset()
        _gameLogic.start()
        currentPositionIndex.value = _gameLogic.position
        isGameStarted.value = _gameLogic.isGameStarted
        isGameOver.value = _gameLogic.gameOver()
    }

    fun makeMove(){
        _gameLogic.makeMove()
        currentPositionIndex.value = _gameLogic.position;
        isGameOver.value = _gameLogic.gameOver()
        if (isGameOver.value)
            isGameStarted.value = false
    }


}