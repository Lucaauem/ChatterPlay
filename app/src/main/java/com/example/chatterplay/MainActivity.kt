package com.example.chatterplay

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterplay.chat.ChatList
import com.example.chatterplay.ui.theme.ChatterPlayTheme
import com.example.chatterplay.user.User
import kotlin.reflect.KFunction0
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    companion object {
        const val APP_VERSION = "0.01a"
    }
    override fun onStart() {
        super.onStart()

        // !TODO! User login
        val session = UserSession.getInstance()
        session.logIn(User("000000"))
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
                    FlowColumn (Modifier
                        .padding(innerPadding)
                        .fillMaxWidth(1.0f)
                        .fillMaxHeight(1.0f),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        FlowRow (
                            Modifier.fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.End
                        ) {
                            UserIcon()
                        }
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth(1.0f),
                            horizontalArrangement = Arrangement.Center,
                            verticalArrangement = Arrangement.spacedBy(
                                space = 20.dp,
                                alignment = Alignment.CenterVertically
                            )
                        ) {
                            NavigationButton("Chatten", ::openChats)
                            NavigationButton("Spielen", ::openGames, false)
                        }
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth(1.0f)
                                .padding(end = 10.dp),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "Version $APP_VERSION",
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.alpha(0.6f)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun NavigationButton(text: String, clickHandler: KFunction0<Unit>, isEnabled: Boolean=true) {
        Button(
            enabled = isEnabled,
            onClick = { clickHandler() },
            modifier = Modifier.fillMaxWidth(0.65f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(vertical = 5.dp),
                fontSize = 27.sp
            )
        }
    }

    @Composable
    private fun UserIcon() {
        Button(
            onClick = { TODO() },
            modifier = Modifier
                .size(85.dp)
                .padding(top = 25.dp, end = 25.dp),
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape,
        ) {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = "User",
                modifier = Modifier.size(85.dp)
            )
        }
    }

    private fun openChats() {
        startActivity(Intent(this, ChatList::class.java))
    }

    private fun openGames() {
        // !TODO!
    }
}
