package com.example.chatterplay.ui.activities.chat

import androidx.compose.runtime.Composable
import com.example.chatterplay.UserSession
import com.example.chatterplay.ui.activities.AppActivity

class ChatActivity : AppActivity() {
    @Composable
    override fun Render() {
        val chatroom = UserSession.getInstance().currentChat
        chatroom!!.Render()
    }
}