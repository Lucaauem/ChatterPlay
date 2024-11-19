package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChatRoom {
    private final int id;
    private final HashMap<Integer, ChatClient> clients = new HashMap<>();

    public ChatRoom(int id) {
        this.id = id;

        // !TODO! Request all users from db

        CommunicationServer.getInstance().addChatRoom(this);
    }

    public void sendMessage(String message) throws IOException {
        for (Map.Entry<Integer, ChatClient> entry : this.clients.entrySet()) {
            entry.getValue().sendMessage(message);
        }
    }

    public void addClient(ChatClient client) {
        this.clients.put(client.getId(), client);

        System.out.println("CHATROOM WITH ID " + this.id + ": NEW CLIENT WITH ID " + client.getId());
        System.out.println("NUMBER OF USERS: " + this.clients.size() + "\n");
    }

    public int getId() {
        return this.id;
    }
}
