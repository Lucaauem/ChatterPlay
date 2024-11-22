package server.database;

import server.chatroom.Chatroom;

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
                new Chatroom("a24652", "Chatroom 1"),
                new Chatroom("b3985t", "Pseudo Chat"),
                new Chatroom("f84kjs", "Private Chat"),
                new Chatroom("c95H4e", "MATSE Chat")
        };
    }
}
