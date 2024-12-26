package com.example.chatterplay.ui.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpMediumButton
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class ChatCreationActivity : AppActivity() {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Render() {
        var textInput by remember { mutableStateOf("") }

        FlowColumn(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.75f),
                    value = textInput,
                    onValueChange = { textInput = it },
                    label = { Text("Name des Chats") },
                    supportingText = { }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CpMediumButton(
                    text = "Erstellen",
                    onClick = { createChatroom(textInput) },
                    enabled = textInput.isNotEmpty()
                )
            }
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