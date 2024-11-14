package com.example.chatterplay.game


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

open class TicTacToe : Game() {
    private var _currentPlayer = 'X'
    override val currentPlayer: Char
        get() = _currentPlayer

    private var _board = mutableStateListOf(
        mutableStateListOf(' ', ' ', ' '),
        mutableStateListOf(' ', ' ', ' '),
        mutableStateListOf(' ', ' ', ' ')
    )

    private var _winner by mutableStateOf<Char?>(null)
    override val winner: Char?
        get() = _winner

    private var isGameOver by mutableStateOf(false)

    override fun playMove(row: Int, col: Int): Boolean {
        if (_board[row][col] != ' ' || isGameOver) {
            return false
        }

        _board[row][col] = _currentPlayer
        _winner = _currentPlayer.takeIf { checkWinner() }

        if (_winner != null || _board.all { it.all { cell -> cell != ' ' } }) {
            isGameOver = true
        } else {
            _currentPlayer = if (_currentPlayer == 'X') 'O' else 'X'
        }
        return true
    }

    override fun resetGame() {
        _board.forEach { row -> row.fill(' ') }
        _currentPlayer = 'X'
        _winner = null
        isGameOver = false
    }

    override fun getBoard(): Array<CharArray> {
        return _board.map { it.toCharArray() }.toTypedArray()
    }

    override fun checkWinner(): Boolean {
        for (row in 0..2) {
            if (_board[row][0] != ' ' &&  _board[row][0] == _board[row][1] && _board[row][1] == _board[row][2]) {
                return true
            }
        }
        for (col in 0..2) {
            if (_board[0][col] != ' ' && _board[0][col] == _board[1][col] && _board[1][col] == _board[2][col]) {
                return true
            }
        }
        if (_board[0][0] != ' ' && _board[0][0] == _board[1][1] && _board[1][1] == _board[2][2]) {
            return true
        }
        if (_board[0][2] != ' ' && _board[0][2] == _board[1][1] && _board[1][1] == _board[2][0]) {
            return true
        }
        return false
    }
    @Composable
    override fun GameUI() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = when {
                    winner != null -> "Player ${winner} Wins!"
                    isGameOver -> "It's a Draw!"
                    else -> "Player $currentPlayer's Turn"
                },
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TicTacToeBoard(_board) { row, col ->
                playMove(row, col)
            }

            if (isGameOver) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { resetGame() }) {
                    Text("Reset Game")
                }
            }
        }
    }



    @Composable
    private fun TicTacToeBoard(board: List<MutableList<Char>>, onCellClick: (Int, Int) -> Unit) {
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
                                text = board[row][col].toString(),
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