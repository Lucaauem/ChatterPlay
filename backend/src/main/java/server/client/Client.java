package server.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import server.RestServer;

public class Client {
    private final String id;
    private DataOutputStream outputStream;

    public Client(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

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
