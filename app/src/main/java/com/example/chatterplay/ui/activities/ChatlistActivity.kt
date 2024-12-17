package com.example.chatterplay.ui.activities

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.chatterplay.UserSession
import com.example.chatterplay.chat.Chatroom

class ChatlistActivity : AppActivity() {
    override fun onStart() {
        super.onStart()
        UserSession.getInstance()
    }

    @Composable
    override fun Render() {
        val chats : HashMap<String, Chatroom> = UserSession.getInstance().chats
        val chatIds: Array<String> = UserSession.getInstance().chats.keys.toTypedArray()

        LazyVerticalGrid(columns = GridCells.Fixed(1)) {
            items(chatIds.size) { index ->
                LoadChat(chats[chatIds[index]])
            }
        }
    }

    @Composable
    private fun LoadChat(chat: Chatroom?) {
        if(chat == null) { return }

        Button(onClick = {
            UserSession.getInstance().joinChat(chat.id)
            ActivityHandler.getInstance().startActivity(this, Activity.CHAT)
        }) {
            Text(chat.name + " [" + chat.id + "]")
        }
    }
}