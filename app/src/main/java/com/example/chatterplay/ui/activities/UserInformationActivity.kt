package com.example.chatterplay.ui.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.chatterplay.ui.components.buttons.CpButtons
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class UserInformationActivity : AppActivity() {
    @Composable
    override fun Render() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                CpButtons.CpGoBackButton(activity = this@UserInformationActivity)
                Spacer(modifier = Modifier.weight(1f))
            }
            UserIcon()
            UserData()
        }
    }

    @Composable
    private fun UserIcon() {
        // !TODO!
    }

    @Composable
    private fun UserData() {
        val tableData = hashMapOf(
            "Vorname" to "Luca",
            "Nachname" to "Außem",
            "Herkunft" to "Düren",
            "Beigetreten" to "Oktober 2024"

        )
        val keys: Array<String> = tableData.keys.toTypedArray()

        val column1Weight = .3f
        val column2Weight = .7f

        LazyColumn(Modifier.padding(0.dp)) {
            items(tableData.size) { index ->
                Row(Modifier.fillMaxWidth()) {
                    TableCell(
                        text = keys[index],
                        weight = column1Weight,
                        fontWeight = FontWeight.Bold
                    )
                    TableCell(
                        text = tableData[keys[index]]!!,
                        weight = column2Weight
                    )
                }
            }
        }
    }

    @Composable
    fun RowScope.TableCell(
        text: String,
        weight: Float,
        modifier: Modifier = Modifier,
        fontWeight: FontWeight = FontWeight.Normal
    ) {
        Text(
            text = text,
            fontWeight = fontWeight,
            modifier = modifier
                .weight(weight)
                .padding(8.dp)
        )
    }
}