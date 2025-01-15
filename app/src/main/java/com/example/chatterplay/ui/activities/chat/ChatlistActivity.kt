package com.example.chatterplay.ui.activities.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatterplay.UserSession
import com.example.chatterplay.chat.Chatroom
import com.example.chatterplay.ui.activities.Activity
import com.example.chatterplay.ui.activities.ActivityHandler
import com.example.chatterplay.ui.activities.AppActivity
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpGoBackButton
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpIconButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ChatlistActivity : AppActivity() {
    private val chats = mutableStateOf(listOf<Chatroom>())

    override fun onStart() {
        super.onStart()
        UserSession.getInstance()
    }

    private fun createChatroom() {
        ActivityHandler.getInstance().startActivity(this, Activity.CHAT_CREATE)
    }

    private fun joinChatroom() {
        ActivityHandler.getInstance().startActivity(this, Activity.CHAT_JOIN)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()

        GlobalScope.launch {
            val req = async { UserSession.getInstance().refreshChatlist() }
            req.await()
            chats.value = UserSession.getInstance().chats.values.toMutableList()
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Render() {
        val chats by remember { this.chats }

        Column {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.End),
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, bottom = 10.dp, top = 10.dp)
            ) {
                CpGoBackButton(activity = this@ChatlistActivity)
                Spacer(Modifier.weight(1f))
                CpIconButton(
                    icon = Icons.Default.Add,
                    description = "Create chat",
                    onClick = { createChatroom() }
                )
                CpIconButton(
                    icon = Icons.Default.Search,
                    description = "Join chat",
                    onClick = { joinChatroom() }
                )
            }
            Row {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1)
                ) {
                    items(chats.size) { index ->
                        LoadChat(chats.sortedBy { it.name }[index])
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun LoadChat(chat: Chatroom?) {
        if(chat == null) { return }

        Button(
            onClick = {
                UserSession.getInstance().joinChat(chat.id)
                ActivityHandler.getInstance().startActivity(this, Activity.CHAT)
            },
            modifier = Modifier.padding(bottom = 1.dp),
            shape = RoundedCornerShape(0.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()){
                FlowColumn {
                    Text(
                        text = chat.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "[" + chat.id + "]",
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.alpha(0.75f)
                    )
                }
            }
        }
    }
}