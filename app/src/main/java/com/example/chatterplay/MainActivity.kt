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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.ui.activities.Activity
import com.example.chatterplay.ui.activities.ActivityHandler
import com.example.chatterplay.ui.activities.LoginActivity
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpBigButton
import com.example.chatterplay.ui.theme.ChatterPlayTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    companion object {
        const val APP_VERSION = "0.90c"
    }
    override fun onStart() {
        super.onStart()

        if(!UserSession.getInstance().isLoggedIn()) {
            val i = Intent(this, LoginActivity::class.java)
            finish()
            startActivity(i)
        }

        UserSession.getInstance().mainActivity = this
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onDestroy() {
        super.onDestroy()

        if(UserSession.getInstance().isLoggedIn()) {
            GlobalScope.launch {
                val req = async { RestService.getInstance().logout() }
                req.await()
                UserSession.getInstance().logout()
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ChatterPlayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FlowColumn (
                        Modifier
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
                            CpBigButton(text = "Chatten", onClick = { openChats() })
                            CpBigButton(text = "Spielen", onClick = { openGames() })
                        }
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth(1.0f)
                                .padding(horizontal = 10.dp),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "Nutzer-ID: ${UserSession.getInstance().user!!.id}",
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.alpha(0.6f)
                            )
                            Spacer(modifier = Modifier.weight(1.0f))
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
    private fun UserIcon() {
        Button(
            onClick = { ActivityHandler.getInstance().startActivity(this, Activity.USER_INFORMATION) },
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
        ActivityHandler.getInstance().startActivity(this, Activity.CHAT_LIST)
    }

    private fun openGames() {
        ActivityHandler.getInstance().startActivity(this, Activity.GAME_LIST)
    }
}
