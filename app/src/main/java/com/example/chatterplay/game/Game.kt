package com.example.chatterplay.game


import androidx.compose.runtime.Composable
import com.example.chatterplay.UserSession
import com.example.chatterplay.communication.RestService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class Game {
    abstract val currentPlayer: Char
    abstract val winner: Char?
    abstract fun playMove(row: Int, col: Int): Boolean
    abstract fun resetGame()
    abstract fun getBoard(): Array<CharArray>
    abstract fun checkWinner(): Boolean
    @Composable
    abstract fun GameUI()

    fun sendTurnToServer(turn: String) {
        GlobalScope.launch {
            val gameId = UserSession.getInstance().currentGameId
            val req = async { RestService.getInstance().gameTurn(gameId, turn) }
        }
    }
}
