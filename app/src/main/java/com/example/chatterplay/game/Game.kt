package com.example.chatterplay.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.chatterplay.UserSession
import com.example.chatterplay.communication.RestService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class Game(protected val mode: GameMode, protected val playerId: Int) {
    companion object {
        const val DEFAULT_WINNER = -1
        const val DEFAULT_BOT_ID = 1
    }

    abstract var state: SnapshotStateList<SnapshotStateList<Int>>
    protected var hasTurn = mutableStateOf(playerId == 0)
    protected var finished by mutableStateOf(false)
    protected var winner = DEFAULT_WINNER // 0 when player has won. 1 when opponent has won

    fun playMove(turn: String) {
        if(!hasTurn.value) {
            return
        }

        val gameUpdate = this.updateGameState(turn, this.playerId)

        if(!(gameUpdate.elementAt(0) as Boolean)) {
            return
        }

        if(this.mode == GameMode.ONLINE) {
            sendTurnToServer(gameUpdate.elementAt(1) as String)
        }

        if(!this.finished) {
            this.hasTurn.value = false
            opponentMove()
        }
    }

    abstract fun updateGameState(move: String, turnmakerId: Int) : Set<Any>

    private fun opponentMove() {
        when(mode) {
            GameMode.OFFLINE -> { botTurn(); this.hasTurn.value = true }
            GameMode.LOCAL -> TODO()
            GameMode.ONLINE -> { }
        }
    }

    fun executeOnlinePlayerTurn(move: String, playerId: Int) {
        if(move == "_RESET") {
            this.resetGame()
            return
        }

        this.updateGameState(move, playerId)
        this.hasTurn.value = true
    }

    abstract fun botTurn()

    abstract fun checkWinner(): Boolean

    abstract fun resetGame()

    @Composable
    abstract fun GameUI()

    @OptIn(DelicateCoroutinesApi::class)
    fun sendTurnToServer(turn: String) {
        GlobalScope.launch {
            val gameId = UserSession.getInstance().currentGameId
            RestService.getInstance().gameTurn(gameId, turn, playerId)
        }
    }
}
