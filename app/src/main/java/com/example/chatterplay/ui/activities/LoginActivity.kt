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
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class LoginActivity : AppActivity() {
    @Composable
    override fun Render() {
        var userIdInput by remember { mutableStateOf("") }
        var userExists by remember { mutableStateOf(true) }

        var ipAddressInput by remember { mutableStateOf("") }
        var couldConnectToServer by remember { mutableStateOf(true) }

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
                    if(!userExists) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 3.dp, bottom = 10.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            text = "Nutzer nicht gefunden!",
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
                onClick = {
                    // Check if server is found
                    val serverConnection : Boolean = connectToServer(ipAddressInput)
                    couldConnectToServer = serverConnection
                    if(!serverConnection) { return@CpMediumButton }

                    UserSession.IP = ipAddressInput

                    // Check if user is found
                    val userFound : Boolean = searchUser(userIdInput)
                    userExists = userFound
                    if(!userFound) { return@CpMediumButton }

                    login(userIdInput)
                },
                enabled = ipAddressInput.isNotEmpty() && userIdInput.isNotEmpty()
            )
        }
    }

    // !TODO! Check if user exists
    private fun login(id: String) {
        val session = UserSession.getInstance()
        session.logIn(User(id))
        val i = Intent(this, MainActivity::class.java)
        finish()
        startActivity(i)
    }

    private fun connectToServer(ipAddress: String) : Boolean {
        try {
            runBlocking {
                async { RestService.testConnection(ipAddress) }.await()
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun searchUser(id: String) : Boolean {
        var userFound = false
        runBlocking {
            val req = async { RestService.getInstance().getUser(id) }
            userFound = req.await()
        }
        return userFound
    }
}