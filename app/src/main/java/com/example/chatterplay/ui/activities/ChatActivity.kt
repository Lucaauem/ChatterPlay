package com.example.chatterplay.ui.activities

import androidx.compose.runtime.Composable
import com.example.chatterplay.UserSession

class ChatActivity : AppActivity() {
    @Composable
    override fun Render() {
        val chatroom = UserSession.getInstance().currentChat
        chatroom!!.Render()
    }
}