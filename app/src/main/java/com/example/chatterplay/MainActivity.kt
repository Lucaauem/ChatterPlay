package com.example.chatterplay

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ChatterPlayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(Modifier
                        .padding(innerPadding)
                        .fillMaxWidth(1.0f)
                    ) {
                        FlowColumn(
                            Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            NavigationButton("Chatten")
                            NavigationButton("Spielen", false)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun NavigationButton(text: String, isEnabled: Boolean=true) {
        Button(
            enabled = isEnabled,
            onClick = { TODO() },
            modifier = Modifier.fillMaxWidth(0.65f)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(vertical = 0.dp),
                fontSize = 20.sp
            )
        }
    }

    private fun openChats() {
        startActivity(Intent(this, ChatList::class.java))
    }
}
