package com.example.chatterplay.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Integer.parseInt

class FourConnect(gameMode: GameMode, playerId: Int) : Game(gameMode, playerId) {
    companion object {
        private const val ROWS = 6
        private const val COLS = 7
    }

    override var state: SnapshotStateList<SnapshotStateList<Int>> = generateBoard()

    private fun generateBoard(): SnapshotStateList<SnapshotStateList<Int>> {
        val outerList = mutableStateListOf<SnapshotStateList<Int>>()

        repeat(ROWS) {
            val innerList = mutableStateListOf<Int>()
            repeat(COLS) {
                innerList.add(-1)
            }
            outerList.add(innerList)
        }

        return outerList
    }

    override fun updateGameState(move: String, turnmakerId: Int): Boolean {
        val col = parseInt("" + move[0])

        if (col < 0 || col >= COLS) {
            return false
        }

        for (r in ROWS - 1 downTo 0) {
            if (this.state[r][col] == -1) {
                this.state[r][col] = turnmakerId


                if (checkWinner()) {
                    this.winner = turnmakerId
                    this.finished = true
                } else if (state.all { row -> row.all { it != -1 } }) {
                    this.finished = true
                }
                return true
            }
        }
        return false
    }

    override fun botTurn() {
        var col = (0 until COLS).random()

        while (state[0][col] != -1) {
            col = (0 until COLS).random()
        }

        updateGameState(col.toString(), DEFAULT_BOT_ID)
    }

    override fun checkWinner(): Boolean {
        for (r in 0 until ROWS) {
            for (c in 0 until COLS) {
                val player = state[r][c]
                if (player != -1 &&
                    (checkDirection(r, c, 1, 0, player) || // Horizontal
                            checkDirection(r, c, 0, 1, player) || // Vertical
                            checkDirection(r, c, 1, 1, player) || // Diagonal (top-left to bottom-right)
                            checkDirection(r, c, 1, -1, player))   // Diagonal (top-right to bottom-left)
                ) {
                    return true
                }
            }
        }
        return false
    }

     private fun checkDirection(row: Int, col: Int, rowIncrement: Int, colIncrement: Int, player: Int): Boolean {
         var count = 0
         var currentRow = row
         var currentCol = col

         while (currentRow in 0 until ROWS && currentCol in 0 until COLS) {
             if (state[currentRow][currentCol] == player) {
                 count++
                 if (count >= 4) {
                     return true
                 }
             } else {
                 count = 0
             }
             currentRow += rowIncrement
             currentCol += colIncrement
         }
         return false
     }

    override fun resetGame() {
        for(i in 0..<ROWS) {
            for(j in 0..<COLS) {
                this.state[i][j] = -1
            }
        }

        this.winner = DEFAULT_WINNER
        this.finished = false
    }

    // !FIXME! Wont update status text after win
    @Composable
    override fun GameUI() {
        val currentPlayer = if (this.hasTurn) this.playerId else ((this.playerId + 1) % 2)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = when {
                    winner != DEFAULT_WINNER -> "Player ${winner + 1} Wins!"
                    finished -> "It's a Draw!"
                    else -> "Player ${currentPlayer + 1}'s Turn"
                },
                fontSize = 28.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                FourConnectBoard(state) { col ->
                    if (!finished) {
                        playMove(col.toString())
                    }
                }

                Button(onClick = { resetGame() }) {
                    Text("Reset Game")
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }

    @Composable
    private fun FourConnectBoard(state: SnapshotStateList<SnapshotStateList<Int>>, onColumnClick: (Int) -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7f / 6f)
                .background(Color.Blue)
                .padding(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (row in state.indices) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (col in state[row].indices) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clickable { onColumnClick(col) },
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .padding(2.dp)
                                ) {
                                    drawCircle(
                                        color = when (state[row][col]) {
                                            0 -> Color.Red
                                            1 -> Color.Yellow
                                            else -> Color.White
                                        },
                                        style = Fill
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
