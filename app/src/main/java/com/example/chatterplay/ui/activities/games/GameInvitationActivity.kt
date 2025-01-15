package com.example.chatterplay.ui.activities.games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatterplay.UserSession
import com.example.chatterplay.UserSession.Companion.getInstance
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.game.GameMode
import com.example.chatterplay.ui.activities.Activity
import com.example.chatterplay.ui.activities.ActivityHandler
import com.example.chatterplay.ui.activities.AppActivity
import com.example.chatterplay.ui.components.buttons.CpButtons
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpBigButton
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpMediumButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GameInvitationActivity : AppActivity() {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Render() {
        var textInput by remember { mutableStateOf("") }

        Row {
            CpButtons.CpGoBackButton(
                activity = this@GameInvitationActivity,
                modifier = Modifier.padding(end = 10.dp, bottom = 10.dp, top = 10.dp))
        }
        FlowColumn(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.75f),
                    value = textInput,
                    onValueChange = { textInput = it },
                    label = { Text("ID des Gegners") },
                    supportingText = { }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CpMediumButton(
                    text = "Einladen",
                    onClick = { invitePlayer(textInput) },
                    enabled = textInput.isNotEmpty()
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CpBigButton(text = "Gegen Bot spielen", onClick = {startOfflineGame()})
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun invitePlayer(oponentId: String) {
        getInstance().openGame(GameMode.ONLINE, 0)
        GlobalScope.launch {
            val req = async { RestService().inviteToGame(oponentId, getInstance().selectedGameAcitvity!!) }
        }
    }

    private fun startOfflineGame() {
        getInstance().openGame(GameMode.OFFLINE, 0)
        ActivityHandler.getInstance().startActivity(getInstance().mainActivity!!, Activity.GAME)
    }
}