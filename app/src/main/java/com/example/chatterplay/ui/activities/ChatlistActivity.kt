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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.chatterplay.UserSession
import com.example.chatterplay.chat.Chatroom

class ChatlistActivity : AppActivity() {
    private val chats = mutableStateOf(listOf<Chatroom>())

    override fun onStart() {
        super.onStart()
        UserSession.getInstance()
    }

    private fun createChatroom() {
        ActivityHandler.getInstance().startActivity(this, Activity.CHAT_CREATE)
    }

    private fun joinChatroom() {
        ActivityHandler.getInstance().startActivity(this, Activity.CHAT_JOIN)
    }

    override fun onResume() {
        super.onResume()

        UserSession.getInstance().refreshChatlist()
        this.chats.value = UserSession.getInstance().chats.values.toMutableList()
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Render() {
        val chats by remember { this.chats }

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
                        onClick = { joinChatroom() },
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
                    items(chats.size) { index ->
                        LoadChat(chats[index])
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