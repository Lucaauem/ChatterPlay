package com.example.chatterplay.communication.socket;

import android.util.Log;
import com.example.chatterplay.UserSession;
import com.example.chatterplay.chat.ChatMessage;
import com.example.chatterplay.game.GameMode;
import com.example.chatterplay.ui.activities.Activity;
import com.example.chatterplay.ui.activities.ActivityHandler;
import com.example.chatterplay.ui.activities.games.GameActivities;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.Timestamp;

public class Receiver extends Thread{
    private final DataInputStream dataInputStream;

    public Receiver(DataInputStream dataInputStream) throws IOException {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        super.run();

        while(true) {
            try {
                String input = this.dataInputStream.readUTF();
                JSONObject data = new JSONObject(input);

                if(data.getString("type").equals("message")) {
                    this.addMessage(data);
                } else if(data.getString("type").equals("invite")) {
                    this.handleGameInvite(data);
                }
            } catch (Exception e) {
                Log.e("DEBUG", e.toString());
                break;
            }
        }
    }

    private void addMessage(JSONObject data) throws JSONException {
        ChatMessage message = new ChatMessage(
                data.getString("id"),
                data.getString("sender"),
                data.getString("senderName"),
                data.getString("content"),
                new Timestamp(data.getLong("timestamp"))
        );

        UserSession.Companion.getInstance().getChats().get(data.getString("chat")).addMessage(message);
    }

    private void handleGameInvite(JSONObject data) throws JSONException {
        String gameId = data.getString("gameId");
        UserSession.Companion.getInstance().setCurrentGameId(gameId);

        assert UserSession.Companion.getInstance().getUser() != null;
        assert UserSession.Companion.getInstance() .getMainActivity() != null;

        if(!data.getString("creatorId").equals(UserSession.Companion.getInstance().getUser().getId())) {
            GameActivities gameType = GameActivities.valueOf(data.getString("gameType"));
            UserSession.Companion.getInstance().openGame(gameType, GameMode.ONLINE, 0); // !TODO! Handle player id stuff
        }

        ActivityHandler.Companion.getInstance().startActivity(UserSession.Companion.getInstance().getMainActivity(), Activity.GAME);
    }
}
