package server.chatroom;

import java.util.ArrayList;
import java.util.HashMap;
import server.database.DatabaseHandler;

public class ChatroomManager {
    public static final int ID_LENGTH = 6;
    private static ChatroomManager instance;

    private final HashMap<String, Chatroom> chatrooms = new HashMap<>();

    private ChatroomManager() {
        this.loadChatrooms();
    }

    public static ChatroomManager getInstance() {
        if(instance == null) {
            instance = new ChatroomManager();
        }
        return instance;
    }

    private void loadChatrooms() {
        Chatroom[] chats = DatabaseHandler.getInstance().getChatrooms();
        for (Chatroom chat : chats) {
            this.chatrooms.put(chat.getId(), chat);
        }
    }

    public Chatroom getChatroom(String id) {
        return this.chatrooms.get(id);
    }

    public Chatroom[] getChatrooms() {
        return this.chatrooms.values().toArray(new Chatroom[0]);
    }

    public Chatroom[] getChatrooms(String clientId) {
        ArrayList<Chatroom> chatroomList = new ArrayList<>();

        for (Chatroom chat : this.chatrooms.values()) {
            if(chat.containsClient(clientId)) {
                chatroomList.add(chat);
            }
        }

        return chatroomList.toArray(new Chatroom[0]);
    }

    public void createChatroom(String name) {
        Chatroom chatroom = new Chatroom(DatabaseHandler.generateId(ID_LENGTH), name, new String[]{});
        DatabaseHandler.getInstance().createChatroom(chatroom);
        this.chatrooms.put(chatroom.getId(), chatroom);
    }

    public void removeChatroom(String id) {
        this.chatrooms.remove(id);
    }
}
