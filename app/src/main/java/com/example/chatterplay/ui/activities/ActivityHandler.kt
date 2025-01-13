package com.example.chatterplay.ui.activities

import android.content.Intent
import androidx.activity.ComponentActivity
import com.example.chatterplay.ui.activities.chat.ChatActivity
import com.example.chatterplay.ui.activities.chat.ChatCreationActivity
import com.example.chatterplay.ui.activities.chat.ChatJoinActivity
import com.example.chatterplay.ui.activities.chat.ChatlistActivity
import com.example.chatterplay.ui.activities.games.EditUserActivity
import com.example.chatterplay.ui.activities.games.GameInvitationActivity
import com.example.chatterplay.ui.activities.games.GameActivitiy
import com.example.chatterplay.ui.activities.games.GameListActivity

class ActivityHandler {
    companion object {
        private var instance : ActivityHandler? = null

        fun getInstance() : ActivityHandler {
            if(instance == null) {
                instance = ActivityHandler()
            }
            return instance as ActivityHandler
        }
    }

    fun startActivity(source: ComponentActivity, target: Activity) {
        val targetClass = when (target){
            Activity.CHAT_CREATE      -> ChatCreationActivity::class.java
            Activity.CHAT_LIST        -> ChatlistActivity::class.java
            Activity.CHAT_JOIN        -> ChatJoinActivity::class.java
            Activity.CHAT             -> ChatActivity::class.java
            Activity.GAME             -> GameActivitiy::class.java
            Activity.GAME_INVITATION  -> GameInvitationActivity::class.java
            Activity.GAME_LIST        -> GameListActivity::class.java
            Activity.USER_EDIT        -> EditUserActivity::class.java
            Activity.USER_INFORMATION -> UserInformationActivity::class.java
        }

        this.startActivity(source, targetClass)
    }

    private fun <T : AppActivity> startActivity(source: ComponentActivity, target : Class<T>) {
        source.startActivity(Intent(source, target))
    }
}