package server.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import server.RestServer;
import server.database.DatabaseHandler;

public class Client {
    private final String id;
    private final String name;
    private DataOutputStream outputStream;

    public Client(String id) {
        Client tmp = DatabaseHandler.getInstance().getClient(id);
        this.id = tmp.getId();
        this.name = tmp.getName();
    }

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() { return this.name; }

    public void setSocket(Socket socket) throws IOException {
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        RestServer.log("Client logged in with id: " + this.id);
    }

    public void sendMessage(String content) {
        try {
            this.outputStream.writeUTF("MSG;" + content);
        } catch (Exception e) {
            System.err.println("Could not send message to client with id: " + this.id);
        }
    }
}
