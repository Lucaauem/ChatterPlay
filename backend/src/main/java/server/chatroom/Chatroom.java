package server.chatroom;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import server.RestServer;
import server.client.Client;
import server.client.ClientManager;
import server.message.Message;

public class Chatroom {
    public static final int ID_LENGTH = 6;
    private final String id;
    private final String name;
    private final HashMap<String, Client> clients = new HashMap<>();

    public Chatroom(String id, String name, String[] clientIds) {
        this.id = id;
        this.name = name;

        ClientManager clientManager = ClientManager.getInstance();
        for(String clientId : clientIds) {
            this.clients.put(clientId, clientManager.getClient(clientId));
        }
    }

    public void addClient(Client client) {
        this.clients.put(client.getId(), client);
        RestServer.log("Client with id " + client.getId() + " joined chatroom with id " + this.id);
    }

     public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void sendMessage(Message message) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("type", "message");
        json.put("id", message.getId());
        json.put("chat", this.id);
        json.put("sender", message.getSender().getId());
        json.put("senderName", message.getSender().getName());
        json.put("content", message.getContent());

        for(Client client : this.clients.values()) {
            if(client == null) continue;
            RestServer.log("Send message to chatroom " + this.id + " and client with id " + client.getId());
            client.sendMessage(json);
        }
    }
}
