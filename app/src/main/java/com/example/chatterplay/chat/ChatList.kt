package com.example.chatterplay.chat

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chatterplay.UserSession
import com.example.chatterplay.ui.theme.ChatterPlayTheme

class ChatList : ComponentActivity() {
    override fun onStart() {
        super.onStart()
        UserSession.getInstance().init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ChatterPlayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        LoadChats()
                    }
                }
            }
        }
    }

    @Composable
    fun LoadChats() {
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
            val intent = Intent(this, ChatActivity::class.java)
            UserSession.getInstance().joinChat(chat.id)
            startActivity(intent)
        }) {
            Text(chat.name + " [" + chat.id + "]")
        }
    }
}
