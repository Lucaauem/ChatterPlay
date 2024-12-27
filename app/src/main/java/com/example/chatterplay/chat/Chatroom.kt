package com.example.chatterplay.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterplay.UserSession
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpIconBackgroundButton
import com.example.chatterplay.ui.theme.RwthBlueMedium
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Chatroom(id: String, name: String) {
    var name: String = name
        private set
    var id: String = id
        private set
    private val messages = mutableStateOf(listOf<ChatMessage>())

    init {
        this.loadRoomMessages()
    }

    private fun loadRoomMessages() {
        runBlocking {
            val req = async { RestService.getInstance().loadMessages(id) }
            val messageList = req.await()

            val tmpList = messages.value.toMutableList()
            messageList.forEach {
                tmpList.add(it)
            }
            messages.value = tmpList
        }
    }

    fun addMessage(message: ChatMessage) {
        val tmpList = this.messages.value.toMutableList()
        tmpList.add(message)
        this.messages.value = tmpList
    }

    @Composable
    fun Render() {
        val messages by remember { this.messages }

        Column {
            StatusBar(name)
            Box(modifier = Modifier.weight(1f)) {
                RenderMessages(messages)
            }
            MessageSendInput()
        }
    }

    private fun sendMessage(input: String) {
        runBlocking {
            val req = async { RestService.getInstance().sendMessage(id, UserSession.getInstance().user!!.id, input) }
            req.await()
        }
    }

    @Composable
    private fun RenderMessages(messages: List<ChatMessage>) {
        val id = UserSession.getInstance().user!!.id
        var previousId = ""

        val gridState = rememberLazyGridState()
        val coroutineScope = rememberCoroutineScope()

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            state = gridState,
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            items(messages.count()) { index ->
                messages[index].ShowMessage(
                    messages[index].isOwnMessage(id),
                    previousId == messages[index].senderId
                )
                previousId = messages[index].senderId
            }
        }

        // Scroll newest message (when opening the chat or while still in it)
        LaunchedEffect(messages.size) {
            if(messages.isEmpty()) {
                return@LaunchedEffect
            }

            coroutineScope.launch {
                gridState.scrollToItem(messages.lastIndex)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MessageSendInput() {
        var textInput by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(top = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.80f),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors( focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent),
                value = textInput,
                onValueChange = { textInput = it },
                placeholder = { Text("Nachricht eingeben...") }
            )
            Spacer(modifier = Modifier.width(7.dp))
            CpIconBackgroundButton(
                icon = Icons.AutoMirrored.Filled.Send,
                description = "Send",
                onClick = { sendMessage(textInput); textInput = "" },
            )
        }
    }

    @Composable
    private fun StatusBar(chatroomName : String) {
        Column(
            modifier = Modifier
                .background(RwthBlueMedium)
                .padding(vertical = 3.dp)
                .height(55.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(1f),
                color =  Color.White,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                text = chatroomName,
            )
            Text(
                modifier = Modifier.fillMaxWidth(1f),
                color = Color.White,
                fontSize = 13.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                text = "[${id}]"
            )
        }
    }
}
