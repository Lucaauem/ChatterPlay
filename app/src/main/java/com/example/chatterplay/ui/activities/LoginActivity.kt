package com.example.chatterplay.ui.activities

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterplay.MainActivity
import com.example.chatterplay.UserSession
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpMediumButton
import com.example.chatterplay.user.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class LoginActivity : AppActivity() {
    private var couldConnectToServer = mutableStateOf(true)
    private var userIsAlreadyLoggedIn = mutableStateOf(false)
    private var userExists = mutableStateOf(true)

    @Composable
    override fun Render() {
        var userIdInput by remember { mutableStateOf("") }
        var ipAddressInput by remember { mutableStateOf("") }

        val userExists by remember { this.userExists }
        val couldConnectToServer by remember { this.couldConnectToServer }
        val userIsAlreadyLoggedIn by remember { this.userIsAlreadyLoggedIn }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.75f),
                value = userIdInput,
                onValueChange = { userIdInput = it },
                label = { Text("Nutzer-ID") },
                supportingText = {
                    if(!userExists || userIsAlreadyLoggedIn) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 3.dp, bottom = 10.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            text = if (userIsAlreadyLoggedIn) "Nutzer bereits angemeldet!" else "Nutzer nicht gefunden!",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.75f),
                value = ipAddressInput,
                onValueChange = { ipAddressInput = it },
                label = { Text("Server-Adresse") },
                supportingText = {
                    if(!couldConnectToServer) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 3.dp, bottom = 10.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            text = "Server nicht gefunden!",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            CpMediumButton(
                text = "Anmelden",
                onClick = { establishConnection(ipAddressInput, userIdInput) },
                enabled = ipAddressInput.isNotEmpty() && userIdInput.isNotEmpty()
            )
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun establishConnection(ipAddressInput: String, userIdInput: String) {
        GlobalScope.launch {
            // Check if server is found
            val serverConnection : Boolean = connectToServer(ipAddressInput)
            couldConnectToServer.value = serverConnection
            if(!serverConnection) { return@launch }

            UserSession.IP = ipAddressInput

            // Check if user is found
            val userFound : Boolean = searchUser(userIdInput)
            userExists.value = userFound
            if(!userFound) { return@launch }

            // Check if user is logged in
            userIsAlreadyLoggedIn.value = !login(userIdInput)

        }
    }

    private suspend fun login(id: String) : Boolean {
        val session = UserSession.getInstance()
        val canConnect = session.logIn(User(id))

        if(canConnect) {
            val i = Intent(this, MainActivity::class.java)
            finish()
            startActivity(i)
        }

        return canConnect
    }

    private suspend fun connectToServer(ipAddress: String) : Boolean {
        return try {
            withTimeout(3500L) {
                withContext(Dispatchers.IO) {
                    RestService.testConnection(ipAddress)
                }
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    private fun searchUser(id: String) : Boolean {
        var userFound = false
        runBlocking {
            val req = async { RestService.getInstance().getUser(id)}
            val user = req.await()
            userFound = user.id != "-1"
        }
        return userFound
    }
}