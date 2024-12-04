package com.example.chatterplay.communication.socket;

import android.util.Log;
import com.example.chatterplay.UserSession;
import com.example.chatterplay.chat.ChatMessage;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.IOException;

public class Receiver extends Thread{
    private final DataInputStream dataInputStream;

    public Receiver(DataInputStream dataInputStream) throws IOException {
        this.dataInputStream = dataInputStream;
        this.dataInputStream.readUTF(); // !FIXME! Gets socket port
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
                data.getString("content")
        );

        UserSession.Companion.getInstance().getChats().get(data.getString("chat")).addMessage(message);
    }
}
