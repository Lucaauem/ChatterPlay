package server.chatroom;

import java.util.HashMap;

import server.RestServer;
import server.client.Client;
import server.client.ClientManager;

public class Chatroom {
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

    public void sendMessage(String content) {
        for(Client client : this.clients.values()) {
            client.sendMessage(content);
        }
    }
}
