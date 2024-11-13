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
    private var chatroom: Chatroom? = null
    private val session = UserSession()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.session.start()
        this.chatroom = this.session.currentChat

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

        val chatIds: Array<String> = this.session.chats.keys.toTypedArray()

        LazyVerticalGrid(columns = GridCells.Fixed(1)) {
            items(chatIds.size) { index ->
                LoadChat(chatIds[index])
            }
        }
    }

    @Composable
    private fun LoadChat(id: String) {
        val session = this.session

        // !TODO! Button to join chat
        Button(onClick = {
            session.joinChat(id)
            val intent = Intent(this, ChatActivity::class.java)
            val bundle = Bundle()
            bundle.putString("chatroomId", id)
            intent.putExtras(bundle)
            startActivity(intent)
        }) {
            Text(id)
        }
    }
}
