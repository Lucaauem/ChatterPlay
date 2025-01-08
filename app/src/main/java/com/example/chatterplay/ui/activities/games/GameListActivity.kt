package com.example.chatterplay.ui.activities.games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatterplay.UserSession
import com.example.chatterplay.ui.activities.Activity
import com.example.chatterplay.ui.activities.ActivityHandler
import com.example.chatterplay.ui.activities.AppActivity
import com.example.chatterplay.ui.components.buttons.CpButtons
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpBigButton

class GameListActivity : AppActivity() {
    companion object {
        private val GAME_TITLES = mapOf(
            GameActivities.CONNECT_FOUR to "Connect Four",
            GameActivities.TIC_TAC_TOE to "Tic Tac Toe"
        )
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Render() {
        FlowColumn {
            Row(modifier = Modifier.padding(bottom = 10.dp)) {
                CpButtons.CpGoBackButton(activity = this@GameListActivity)
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                items(GameActivities.entries.size) { index ->
                    val game = GameActivities.entries[index]

                    CpBigButton(
                        text = GAME_TITLES[game].toString(),
                        onClick = { startGame(game) }
                    )
                }
            }
        }
    }

    private fun startGame(game: GameActivities) {
        ActivityHandler.getInstance().startActivity(this, Activity.GAME_INVITATION)
        //UserSession.getInstance().openGame(game)
        //ActivityHandler.getInstance().startActivity(this, Activity.GAME)
    }
}