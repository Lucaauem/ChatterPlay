package com.example.chatterplay.ui.activities.chat

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.ui.activities.AppActivity
import com.example.chatterplay.ui.components.buttons.CpButtons
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpMediumButton
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class ChatJoinActivity : AppActivity() {
    private fun joinChatroom(chatId: String) : Boolean {
        var chatFound = false
        runBlocking {
            val req = async { RestService.getInstance().joinChatroom(chatId) }
            val status : Boolean = req.await()

            if(status) {
                finish()
                chatFound = true
            }
        }

        return chatFound
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Render() {
        var textInput by remember { mutableStateOf("") }
        var chatroomFound by remember { mutableStateOf(true) }

        fun validate(status : Boolean) {
            chatroomFound = status
        }

        Row {
            CpButtons.CpGoBackButton(
                activity = this@ChatJoinActivity,
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
                    label = { Text("ID des Chats") },
                    supportingText = {
                        if(!chatroomFound) {
                            Text(
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                text = "Chat not found!",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CpMediumButton(
                    text = "Beitreten",
                    onClick = { validate(joinChatroom(textInput)) },
                    enabled = textInput.isNotEmpty()
                )
            }
        }
    }
}