
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterplay.game.Game

 class FourConnect : Game() {
     private val rows = 6
     private val cols = 7

     private var _board = mutableStateListOf(
         MutableList(cols) { ' ' },
         MutableList(cols) { ' ' },
         MutableList(cols) { ' ' },
         MutableList(cols) { ' ' },
         MutableList(cols) { ' ' },
         MutableList(cols) { ' ' }
     )

     private var _currentPlayer by mutableStateOf('X')
     override val currentPlayer: Char
         get() = _currentPlayer

     private var _winner by mutableStateOf<Char?>(null)
     override val winner: Char?
         get() = _winner

     private var isGameOver by mutableStateOf(false)

     override fun playMove(row: Int, col: Int): Boolean {
         if (col < 0 || col >= cols || isGameOver) return false

         for (r in rows - 1 downTo 0) {
             if (_board[r][col] == ' ') {
                 _board[r][col] = _currentPlayer


                 if (checkWinner()) {
                     _winner = _currentPlayer
                     isGameOver = true
                 } else if (_board.all { row -> row.all { it != ' ' } }) {
                     isGameOver = true
                 } else {
                     _currentPlayer = if (_currentPlayer == 'X') 'O' else 'X'
                 }
                 return true
             }
         }
         return false
     }

     override fun resetGame() {
         _board = mutableStateListOf(
             MutableList(cols) { ' ' },
             MutableList(cols) { ' ' },
             MutableList(cols) { ' ' },
             MutableList(cols) { ' ' },
             MutableList(cols) { ' ' },
             MutableList(cols) { ' ' }
         )
         _currentPlayer = 'X'
         _winner = null
         isGameOver = false
     }

     override fun checkWinner(): Boolean {
         for (r in 0 until rows) {
             for (c in 0 until cols) {
                 val player = _board[r][c]
                 if (player != ' ' &&
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

     private fun checkDirection(row: Int, col: Int, rowIncrement: Int, colIncrement: Int, player: Char): Boolean {
         var count = 0
         var currentRow = row
         var currentCol = col

         while (currentRow in 0 until rows && currentCol in 0 until cols) {
             if (_board[currentRow][currentCol] == player) {
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
    override fun getBoard(): Array<CharArray> = _board.map { it.toCharArray() }.toTypedArray()

    @Composable
    override fun GameUI() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = when {
                    winner != null -> "Player ${winner} Wins!"
                    isGameOver -> "It's a Draw!"
                    else -> "Player $currentPlayer's Turn"
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
                FourConnectBoard { col ->
                    if (!isGameOver) {
                        playMove(0, col)
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
    private fun FourConnectBoard(
        onColumnClick: (Int) -> Unit
    ) {
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
                for (row in _board.indices) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (col in _board[row].indices) {
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
                                        color = when (_board[row][col]) {
                                            'X' -> Color.Red
                                            'O' -> Color.Yellow
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
