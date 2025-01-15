package com.example.chatterplay.ui.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatterplay.UserSession
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.communication.UserData
import com.example.chatterplay.communication.UserUpdate
import com.example.chatterplay.ui.components.buttons.CpButtons
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EditUserActivity : AppActivity() {
    private var data: MutableState<UserData> = mutableStateOf(UserData("", "", "", "", ""))

    @OptIn(ExperimentalLayoutApi::class, DelicateCoroutinesApi::class)
    @Composable
    override fun Render() {
        runBlocking {
            val req = async { RestService.getInstance().getUser(UserSession.getInstance().user!!.id) }
            data.value = req.await()
        }

        var firstNameInput by remember { mutableStateOf(data.value.firstName) }
        var lastNameInput by remember { mutableStateOf(data.value.lastName) }
        var originInput by remember { mutableStateOf(data.value.origin) }

        Box(modifier = Modifier.fillMaxSize()) {
            CpButtons.CpGoBackButton(
                activity = this@EditUserActivity,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            )
        }

        FlowColumn(
            verticalArrangement = Arrangement.spacedBy(space = 20.dp, alignment = Alignment.CenterVertically),
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            FlowColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.75f),
                    value = firstNameInput,
                    onValueChange = { firstNameInput = it },
                    label = { Text("Vorname") }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.75f),
                    value = lastNameInput,
                    onValueChange = { lastNameInput = it },
                    label = { Text("Nachname") }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.75f),
                    value = originInput,
                    onValueChange = { originInput = it },
                    label = { Text("Ort") }
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CpButtons.CpMediumButton(text = "Speichern", onClick = {
                    GlobalScope.launch {
                        val id = UserSession.getInstance().user!!.id
                        val req = async { RestService.getInstance().updatePlayer(UserUpdate(id, firstNameInput, lastNameInput,originInput)) }
                        req.await()

                        finish()
                    }
                })
            }
        }
    }
}