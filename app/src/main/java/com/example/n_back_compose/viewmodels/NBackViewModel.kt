package com.example.n_back_compose.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.n_back_compose.model.GameLogic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class NBackViewModel(private val dataStore: DataStore<Preferences>) : ViewModel(){

    private var _gameLogic : GameLogic = GameLogic.instance!!

    var currentPositionIndex : MutableState<Int> = mutableStateOf(_gameLogic.position)

    var isGameOver : MutableState<Boolean> = mutableStateOf(_gameLogic.gameOver())
    var isGameStarted : MutableState<Boolean> = mutableStateOf(_gameLogic.isGameStarted)
     var _valueOfN : Flow<Any> = dataStore.data
        .map { preferences ->
            preferences[VALUE_OF_N]?.let { Log.i("In viewmodel", it) }
            val uiMode = preferences[VALUE_OF_N] ?: "2"
            uiMode
        }

     var _selectedStimuli : Flow<Any> = dataStore.data
        .map { preferences ->
            val selectedStimuli = preferences[SELECTED_STIMULI]?:"Visual"
            selectedStimuli
        }
     var _timeBetweenEvents : Flow<Any> = dataStore.data
        .map { preferences ->
            val timeBetweenEvents = preferences[TIME_BETWEEN_EVENTS]?:"1000"
            timeBetweenEvents
        }

    var valueOfN : MutableState<String> = mutableStateOf("")
    var timeBetweenEvents : MutableState<String> = mutableStateOf("")
    var selectedStimuli : MutableState<String> = mutableStateOf("")






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

    fun getSettings(){
        /*viewModelScope.launch {
            _valueOfN = dataStore.data.map { preferences -> preferences[VALUE_OF_N]!! }
            valueOfN.value = _valueOfN.first().toString()
            _selectedStimuli = dataStore.data.map { preferences -> preferences[SELECTED_STIMULI]!! }
            selectedStimuli.value = _selectedStimuli.first().toString()
            _timeBetweenEvents = dataStore.data.map { preferences -> preferences[TIME_BETWEEN_EVENTS]!! }
            timeBetweenEvents.value = _timeBetweenEvents.first().toString()
        }*/
    }
    companion object{
        val TIME_BETWEEN_EVENTS = stringPreferencesKey("sp1")
        val SELECTED_STIMULI = stringPreferencesKey("dd1")
        val VALUE_OF_N = stringPreferencesKey("l1")
    }


}