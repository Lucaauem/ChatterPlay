package server.client;

import java.util.HashMap;
import server.RestServer;
import server.database.DatabaseHandler;

public class ClientManager {
    private static ClientManager instance = null;

    private final HashMap<String, Client> clients = new HashMap<>();

    private ClientManager() {
        Client[] clients = DatabaseHandler.getInstance().getClients();

        for(Client client : clients) {
            this.clients.put(client.getId(), client);
        }
    }

    public static ClientManager getInstance() {
        if(instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    public void addClient(Client client) {
        if(!this.clients.containsKey(client.getId())) {
            this.clients.put(client.getId(), client);
        }
        RestServer.log("Client wants to log in with id: " + client.getId());
    }

    public Client getClient(String id) {
        return this.clients.get(id);
    }

    public void updateClient(Client client) {
        this.clients.put(client.getId(), client);
        DatabaseHandler.getInstance().updateClient(client);
    }

    public void removeClient(String id) {
        this.clients.remove(id);
        DatabaseHandler.getInstance().removeClient(id);
    }
}
