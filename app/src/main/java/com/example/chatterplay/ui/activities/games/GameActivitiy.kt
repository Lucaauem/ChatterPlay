package com.example.chatterplay.ui.activities.games

import androidx.compose.runtime.Composable
import com.example.chatterplay.UserSession
import com.example.chatterplay.ui.activities.AppActivity

class GameActivitiy : AppActivity() {
    @Composable
    override fun Render() {
        UserSession.getInstance().selectedGame!!.GameUI()
    }
}