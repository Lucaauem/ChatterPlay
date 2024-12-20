package com.example.chatterplay.ui.activities

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.chatterplay.communication.RestService
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class ChatCreationActivity : AppActivity() {
    @Composable
    override fun Render() {
        var textInput by remember { mutableStateOf("") }

        TextField(
            modifier = Modifier.fillMaxWidth(0.75f),
            value = textInput,
            onValueChange = { textInput = it },
            label = { Text("Name des Chats") }
        )
        Button(
            enabled = textInput.isNotEmpty(),
            onClick = { createChatroom(textInput) }
        ) {
            Text("Erstellen")
        }
    }

    private fun createChatroom(name: String) {
        runBlocking {
            val req = async { RestService().createChatroom(name) }
            req.await()
            finish()
        }
    }
}