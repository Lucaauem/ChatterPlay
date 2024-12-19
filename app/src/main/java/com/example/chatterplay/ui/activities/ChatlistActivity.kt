package com.example.chatterplay.ui.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.chatterplay.UserSession
import com.example.chatterplay.chat.Chatroom

class ChatlistActivity : AppActivity() {
    override fun onStart() {
        super.onStart()
        UserSession.getInstance()
    }

    private fun createChatroom() {
        ActivityHandler.getInstance().startActivity(this, Activity.CHAT_CREATE)
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Render() {
        val chats : HashMap<String, Chatroom> = UserSession.getInstance().chats
        val chatIds: Array<String> = UserSession.getInstance().chats.keys.toTypedArray()

        FlowColumn {
            Row(modifier = Modifier.zIndex(10f)) {
                Column {
                    Button(
                        onClick = { createChatroom() },
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .fillMaxWidth(0.5f)
                    ) {
                        Text("Chat erstellen")
                    }
                }
                Column {
                    Button(
                        enabled = false,
                        onClick = { TODO() },
                        modifier = Modifier
                            .padding(start = 5.dp, end = 5.dp, bottom = 15.dp)
                            .fillMaxWidth(1f)

                    ) {
                        Text("Chat beitreten")
                    }
                }
            }
            Row {
                LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                    items(chatIds.size) { index ->
                        LoadChat(chats[chatIds[index]])
                    }
                }
            }
        }
    }

    @Composable
    private fun LoadChat(chat: Chatroom?) {
        if(chat == null) { return }

        Button(
            onClick = {
                UserSession.getInstance().joinChat(chat.id)
                ActivityHandler.getInstance().startActivity(this, Activity.CHAT)
            },
            modifier = Modifier.padding(horizontal = 5.dp)
        ) {
            Text(chat.name + " [" + chat.id + "]")
        }
    }
}