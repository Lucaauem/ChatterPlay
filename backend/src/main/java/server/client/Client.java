package server.client;

import java.net.Socket;

import server.RestServer;

public class Client {
    private final String id;

    public Client(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setSocket(Socket socket) {
        RestServer.log("Client logged in with id: " + this.id);
    }
}
