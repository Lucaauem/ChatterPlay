package com.example.chatterplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.chatterplay.chat.ChatList
import com.example.chatterplay.chat.Chatroom
import com.example.chatterplay.ui.theme.ChatterPlayTheme

class MainActivity : ComponentActivity() {
    private var chatroom: Chatroom? = null
    private val session = UserSession()

    override fun onStart() {
        super.onStart()
        this.session.start()
        this.session.joinChat("cr_12345")
        this.chatroom = this.session.currentChat
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chats = ChatList(this.session)

        enableEdgeToEdge()
        setContent {
            ChatterPlayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        chats.LoadChats()
                        // this.chatroom!!.Render(Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
