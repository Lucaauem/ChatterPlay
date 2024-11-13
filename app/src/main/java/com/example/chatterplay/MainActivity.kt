package com.example.chatterplay

import android.content.Intent
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
import com.example.chatterplay.ui.theme.ChatterPlayTheme
import com.example.chatterplay.user.User
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()

        // !TODO! User login
        val session = UserSession.getInstance()
        session.logIn(User(1))
        if(!session.isLoggedIn()) {
            exitProcess(0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ChatterPlayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        // Menu Menu //
                    }
                }
            }
        }

        startActivity(Intent(this, ChatList::class.java))
    }
}
