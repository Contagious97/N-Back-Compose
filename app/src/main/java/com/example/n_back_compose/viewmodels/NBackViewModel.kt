package com.example.n_back_compose.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import com.example.n_back_compose.model.GameLogic
import kotlinx.coroutines.flow.map


class NBackViewModel(dataStore: DataStore<Preferences>) : ViewModel(){

    private var _gameLogic : GameLogic = GameLogic.instance!!

    var currentPositionIndex : MutableState<Int> = mutableStateOf(_gameLogic.position)

    var isGameOver : MutableState<Boolean> = mutableStateOf(_gameLogic.gameOver())
    var isGameStarted : MutableState<Boolean> = mutableStateOf(_gameLogic.isGameStarted)
    var _valueOfN = dataStore.data.map { it[TIME_BETWEEN_EVENTS] }



    //var valueOfN : MutableState<Int> = mutableStateOf(_valueOfN.first()!)

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


    companion object{
        val TIME_BETWEEN_EVENTS = stringPreferencesKey("sp1")

    }


}