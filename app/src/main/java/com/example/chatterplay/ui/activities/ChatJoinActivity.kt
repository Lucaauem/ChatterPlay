package com.example.chatterplay.ui.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class ChatJoinActivity : AppActivity() {
    private fun joinChatroom(chatId: String) : Boolean {
        runBlocking {
            val req = async { RestService.getInstance().joinChatroom(chatId) }
            val status : Boolean = req.await()

            if(status) {
                finish()
            }

        }

        return false
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Render() {
        var textInput by remember { mutableStateOf("") }
        var chatroomFound by remember { mutableStateOf(true) }

        fun validate(status : Boolean) {
            chatroomFound = status
        }

        FlowColumn(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TextField(
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
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    enabled = textInput.isNotEmpty(),
                    onClick = { validate(joinChatroom(textInput)) }
                ) {
                    Text(
                        text = "Beitreten",
                        modifier = Modifier.padding(4.dp),
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}