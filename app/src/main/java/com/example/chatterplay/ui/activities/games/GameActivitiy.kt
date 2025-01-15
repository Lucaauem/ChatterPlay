package com.example.chatterplay.ui.activities.games

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatterplay.UserSession
import com.example.chatterplay.ui.activities.AppActivity
import com.example.chatterplay.ui.components.buttons.CpButtons

class GameActivitiy : AppActivity() {
        @Composable
        override fun Render() {

            Box(modifier = Modifier.fillMaxSize()) {
                CpButtons.CpGoBackButton(
                    activity = this@GameActivitiy,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
            }


            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UserSession.getInstance().selectedGame?.GameUI()
            }
        }
    }

