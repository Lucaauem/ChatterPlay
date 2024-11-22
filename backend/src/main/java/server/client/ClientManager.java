package server.client;

import java.util.HashMap;

import server.RestServer;

public class ClientManager {
    private static ClientManager instance = null;

    private final HashMap<String, Client> clients = new HashMap<>();

    private ClientManager() {}

    public static ClientManager getInstance() {
        if(instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    public void addClient(Client client) {
        this.clients.put(client.getId(), client);
        RestServer.log("Client wants to log in with id: " + client.getId());
    }

    public Client getClient(String id) {
        return this.clients.get(id);
    }
}
