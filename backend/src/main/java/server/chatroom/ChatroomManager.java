package server.chatroom;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;
import server.database.DatabaseHandler;

public class ChatroomManager {
    private static final int ID_LENGTH = 6;
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

    public Chatroom[] getChatrooms() {
        return this.chatrooms.values().toArray(new Chatroom[0]);
    }

    public void createChatroom(String name) {
        Chatroom chatroom = new Chatroom(this.generateId(), name, new String[]{});
        this.chatrooms.put(chatroom.getId(), chatroom);
    }

    public void removeChatroom(String id) {
        this.chatrooms.remove(id);
    }

    private String generateId() {
        try {
            while(true) {
                long time = System.nanoTime();
                Random random = new Random();

                long randomValue = random.nextLong();
                String input = time + String.valueOf(randomValue);

                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] hash = md.digest(input.getBytes());

                StringBuilder hexString = new StringBuilder();
                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }

                String id = hexString.substring(0, ID_LENGTH);

                if(!this.chatrooms.containsKey(id)) {
                    return id;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm");
            return "";
        }

    }
}
