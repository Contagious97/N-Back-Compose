package com.example.n_back_compose.model

import android.util.Log
import kotlin.random.Random

class GameLogic private constructor(private var n_back: Int, private var rounds: Int) {
    private var positions = IntArray(rounds)
    private var auditory = CharArray(rounds)
    var currPosition = 0
        private set
    var isGameStarted = false
    private var isAuditory: Boolean
    private val letters = charArrayOf('C', 'K', 'L', 'M', 'T', 'G', 'O')
    private fun generateAuditory() {
        auditory = CharArray(rounds)
        val random = Random.Default
        var randomIndex = -1
        for (i in auditory.indices) {
            randomIndex = random.nextInt(7)
            auditory[i] = letters[randomIndex]
        }
    }

    private fun generatePositions() {
        positions = IntArray(rounds)
        val random = Random.Default
        for (i in positions.indices) {
            positions[i] = random.nextInt(9)
            Log.i(TAG, "Pos: " + positions[i])
        }

        /*int codedPosistions[] = new int[]{1,4,1,3,5,3,7,3,8,4,8,2,1,5,1,6,6,7,6,7};

        int codedPositions2[] = new int[]{1,2,1,4,3,4};
        for (int i = 0; i<positions.length; i++){
            if (rounds == 6)
                positions[i] = codedPositions2[i];
            else
                positions[i] = codedPosistions[i];
        }*/
    }

    private fun isCorrectGuess(): Boolean {
        if (isAuditory) {
            if (n_backValue < 0) return false
            Log.i(
                TAG,
                "Curr value: " + auditory[if (currPosition == auditory.size) currPosition - 1 else currPosition]
            )
            Log.i(TAG, "N-back value: $n_backValue")
            return auditory[currPosition].code == n_backValue
        } else {
            if (n_backValue < 0) return false
            Log.i(TAG, "Curr value: " + positions[currPosition])
            Log.i(TAG, "N-back value: $n_backValue")
            return positions[currPosition] == n_backValue
        }
    }


    fun makeMove() {
        currPosition++
        Log.i(TAG, "currPosition: ${positions[if(currPosition>=10)0 else currPosition]}")
    }

    fun gameOver(): Boolean {
        if (currPosition == rounds) isGameStarted = false
        return currPosition == rounds
    }

    val position: Int
        get() = if (isAuditory) {
            if (currPosition > auditory.size - 1)  auditory[auditory.size - 1].toInt()
            if (currPosition == -1) -1 else auditory[currPosition].toInt()
        } else {
            if (currPosition >= positions.size - 1)  positions[positions.size - 1]
            else if (currPosition == -1) -1 else positions[currPosition]
        }
    val letter: Char
        get() {
            if (currPosition > auditory.size - 1) return auditory[auditory.size - 1]
            return if (currPosition == -1) Character.MIN_VALUE else auditory[currPosition]
        }
    val prevValue: Int
        get() = if (isAuditory) {
            if (currPosition < 1) -1 else auditory[currPosition - 1].toInt()
        } else {
            if (currPosition < 1) -1 else positions[currPosition - 1]
        }
    private val n_backValue: Int
        get() = if (isAuditory) {
            if (currPosition >= n_back) {
                auditory[currPosition - n_back].code
            } else Int.MIN_VALUE
        } else {
            if (currPosition >= n_back) positions[currPosition - n_back] else Int.MIN_VALUE
        }



    fun start() {
        isGameStarted = true
        isAuditory = false //GameSettings.isAudioStimuli()
        if (isAuditory) {
            generateAuditory()
        } else generatePositions()

        //generateAuditory();
        //isAuditory = false;
        //rounds = GameSettings.getNrOfEvents();
        rounds = 10//GameSettings.getNrOfEvents()
        n_back = 2//GameSettings.getValueOfN()
        currPosition = -1
    }

    fun reset() {
        /*for (int i = 0; i<positions.length; i++){
            positions[i] = 0;
        }*/
        isGameStarted = true
        currPosition = -1
    }

    companion object {
        const val SIZE = 3
        private const val TAG = "GAME_LOGIC"
        private var gameLogic: GameLogic? = null
        val instance: GameLogic?
            get() {
                if (gameLogic == null) {
                    gameLogic = GameLogic(2, 10)
                }
                return gameLogic
            }
    }

    init {
        isAuditory = false//GameSettings.isAudioStimuli()
    }
}