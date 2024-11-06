package com.example.chatterplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.chatterplay.chat.Chatroom
import com.example.chatterplay.ui.theme.ChatterPlayTheme

class MainActivity : ComponentActivity() {
    private var chatroom: Chatroom? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatterPlayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    this.chatroom = Chatroom(Modifier.padding(innerPadding))
                    this.chatroom!!.Render()
                }
            }
        }
    }
}
