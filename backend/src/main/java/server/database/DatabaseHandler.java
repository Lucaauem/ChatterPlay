package server.database;

import server.chatroom.Chatroom;
import server.message.Message;

public class DatabaseHandler {
    private static DatabaseHandler instance = null;

    private DatabaseHandler() {}

    public static DatabaseHandler getInstance() {
        if(instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    // !TODO! Get data from database
    public Chatroom[] getChatrooms() {
        return new Chatroom[] {
                new Chatroom("a24652", "Chatroom 1",   new String[]{}),
                new Chatroom("b3985t", "Pseudo Chat",  new String[]{}),
                new Chatroom("f84kjs", "Private Chat", new String[]{}),
                new Chatroom("c95H4e", "MATSE Chat",   new String[]{})
        };
    }

    public void addMessage(Message message) {
        // !TODO! Add data to database
    }

    public Message[] getMessages(String chatId) {
        return new Message[] {
                new Message("dj40sj", chatId, "ss784h", "Hallo!")
        };
    }
}
