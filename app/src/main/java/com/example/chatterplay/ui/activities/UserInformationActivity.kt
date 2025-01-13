package com.example.chatterplay.ui.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.chatterplay.R
import com.example.chatterplay.UserSession
import com.example.chatterplay.communication.RestService
import com.example.chatterplay.communication.UserData
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpGoBackButton
import com.example.chatterplay.ui.components.buttons.CpButtons.Companion.CpIconButton
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class UserInformationActivity : AppActivity() {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Render() {
        Box(modifier = Modifier.fillMaxSize().padding(start = 10.dp)) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.End),
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, bottom = 10.dp, top = 10.dp)
            ) {
                CpGoBackButton(activity = this@UserInformationActivity)
                Spacer(Modifier.weight(1f))
                CpIconButton(
                    icon = Icons.Default.Edit,
                    onClick = { editUserData() },
                    description = "Edit Account Data"
                )
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-85).dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserIcon()
            Spacer(modifier = Modifier.height(25.dp))
            UserData()
        }
    }

    @Composable
    private fun UserIcon() {
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = "Account Image",
            modifier = Modifier.size(200.dp)
        )
    }

    @Composable
    private fun UserData() {
        val data: UserData
        runBlocking {
            val req = async { RestService.getInstance().getUser(UserSession.getInstance().user!!.id) }
            data = req.await()
        }

        // Format date
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormatter = SimpleDateFormat("MMMM yyyy", Locale.GERMAN)
        outputFormatter.timeZone = TimeZone.getDefault()
        val formattedDate = outputFormatter.format(sdf.parse(data.joined)!!)

        val tableData = linkedMapOf(
            "Vorname"     to data.firstName,
            "Nachname"    to data.lastName,
            "Ort"         to data.origin,
            "Beigetreten" to formattedDate
        )
        val keys: Array<String> = tableData.keys.toTypedArray()

        LazyColumn(Modifier.padding(0.dp).fillMaxWidth(0.625f)) {
            items(tableData.size) { index ->
                Row(Modifier.fillMaxWidth()) {
                    TableCell(
                        text = keys[index],
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(0.4f)
                    )
                    TableCell(
                        text = tableData[keys[index]]!!.toString(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    @Composable
    private fun TableCell(
        text: String,
        modifier: Modifier = Modifier,
        fontWeight: FontWeight = FontWeight.Normal
    ) {
        Text(
            text = text,
            fontWeight = fontWeight,
            modifier = modifier
                .padding(8.dp)
        )
    }

    private fun editUserData() {
        // !TODO!
    }
}