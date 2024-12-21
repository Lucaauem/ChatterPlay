package com.example.chatterplay.ui.activities

import android.content.Intent
import androidx.activity.ComponentActivity

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
            Activity.CHAT_CREATE -> ChatCreationActivity::class.java
            Activity.CHAT_LIST   -> ChatlistActivity::class.java
            Activity.CHAT_JOIN   -> ChatJoinActivity::class.java
            Activity.CHAT        -> ChatActivity::class.java
        }

        this.startActivity(source, targetClass)
    }

    private fun <T : AppActivity> startActivity(source: ComponentActivity, target : Class<T>) {
        source.startActivity(Intent(source, target))
    }
}