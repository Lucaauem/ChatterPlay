package com.example.chatterplay.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Integer.parseInt

open class TicTacToe(gameMode: GameMode, playerId: Int) : Game(gameMode, playerId) {
    companion object {
        private val PLAYER_ICONS = listOf('X', 'O')
    }

    override var state: SnapshotStateList<SnapshotStateList<Int>> = mutableStateListOf(
        mutableStateListOf(-1, -1, -1),
        mutableStateListOf(-1, -1, -1),
        mutableStateListOf(-1, -1, -1)
    )

    override fun updateGameState(move: String, turnmakerId: Int) : Set<Any> {
        val row = parseInt("" + move[0])
        val col = parseInt("" + move[1])

        if (this.state[row][col] != -1) {
            return setOf(false, "")
        }

        this.state[row][col] = turnmakerId

        if(this.checkWinner())  {
            this.winner = turnmakerId
            this.finished = true
        } else if (state.all { it.all { cell -> cell != -1 } }) {
            this.finished = true
        }

        return setOf(true, move)
    }

    override fun botTurn() {
        while (true) {
            val row = (0..2).random()
            val col = (0..2).random()

            if (state[row][col] == -1) {
                updateGameState("$row$col", DEFAULT_BOT_ID)
                return
            }
        }
    }

    override fun checkWinner(): Boolean {
        for (row in 0..2) {
            if (state[row][0] != -1 &&  state[row][0] == state[row][1] && state[row][1] == state[row][2]) {
                return true
            }
        }
        for (col in 0..2) {
            if (state[0][col] != -1 && state[0][col] == state[1][col] && state[1][col] == state[2][col]) {
                return true
            }
        }
        if (state[0][0] != -1 && state[0][0] == state[1][1] && state[1][1] == state[2][2]) {
            return true
        }
        if (state[0][2] != -1 && state[0][2] == state[1][1] && state[1][1] == state[2][0]) {
            return true
        }
        return false
    }

    override fun resetGame() {
        this.state.forEach { row -> row.fill(-1) }
        this.winner = DEFAULT_WINNER
        this.hasTurn.value = playerId == 0
        this.finished = false
    }

    @Composable
    override fun GameUI() {

        val currentTurn by remember { this.hasTurn }
        val currentPlayerIcon = if (currentTurn) PLAYER_ICONS[this.playerId] else PLAYER_ICONS[(this.playerId + 1) % 2]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = when {
                    winner != DEFAULT_WINNER -> "Player ${winner + 1} Wins!"
                    finished -> "It's a Draw!"
                    else -> "Player $currentPlayerIcon's Turn"
                },
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TicTacToeBoard(state) { row, col ->
                playMove("" + row + col)
            }

            if (finished && playerId == 0) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    if(mode == GameMode.ONLINE) {
                        sendTurnToServer("_RESET")
                    }
                    resetGame()
                }) {
                    Text("Reset Game")
                }
            }
        }
    }

    @Composable
    private fun TicTacToeBoard(board: SnapshotStateList<SnapshotStateList<Int>>, onCellClick: (Int, Int) -> Unit) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            for (row in 0..2) {
                Row {
                    for (col in 0..2) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .border(2.dp, Color.Black)
                                .background(Color.LightGray)
                                .clickable { onCellClick(row, col) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (board[row][col] != -1) PLAYER_ICONS[board[row][col]].toString() else "",
                                fontSize = 32.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}