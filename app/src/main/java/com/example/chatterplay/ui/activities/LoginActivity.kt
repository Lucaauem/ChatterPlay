package com.example.chatterplay.ui.activities

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
import com.example.chatterplay.UserSession
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpMediumButton
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class LoginActivity : AppActivity() {
    @Composable
    override fun Render() {
        var textInput by remember { mutableStateOf("") }
        var chatroomFound by remember { mutableStateOf(true) }


        var ipAddressInput by remember { mutableStateOf("") }
        var couldConnectToServer by remember { mutableStateOf(true) }

        fun validate(status : Boolean) {
            chatroomFound = status
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.75f),
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Nutzer-ID") },
                supportingText = {
                    if(!chatroomFound) {
                        Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            fontSize = 20.sp,
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
                onClick = { couldConnectToServer = (login(ipAddressInput)) },
                //enabled = textInput.isNotEmpty()
            )
        }
    }

    private fun login(ipAddress: String) : Boolean {
        return connectToServer(ipAddress)
    }

    private fun connectToServer(ipAddress: String) : Boolean {
        try {
            runBlocking {
                async { RestService.testConnection(ipAddress) }.await()
            }
            UserSession.IP = ipAddress
            return true
        } catch (e: Exception) {
            return false
        }
    }
}