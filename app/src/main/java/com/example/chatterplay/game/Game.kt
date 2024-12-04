package com.example.chatterplay.game


import androidx.compose.runtime.Composable

abstract class Game {
    abstract val currentPlayer: Char
    abstract val winner: Char?
    abstract fun playMove(row: Int, col: Int): Boolean
    abstract fun resetGame()
    abstract fun getBoard(): Array<CharArray>
    abstract fun checkWinner(): Boolean
    @Composable
    abstract fun GameUI()
}
