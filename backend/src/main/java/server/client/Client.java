package server.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Date;

import org.json.JSONObject;
import server.RestServer;
import server.database.DatabaseHandler;

public class Client {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String origin;
    private final Date joined;

    private DataOutputStream outputStream;

    public Client(String id) {
        Client tmp = DatabaseHandler.getInstance().getClient(id);
        this.id = tmp.getId();
        this.firstName = tmp.getFirstName();
        this.lastName = tmp.getLastName();
        this.origin = tmp.getOrigin();
        this.joined = tmp.getJoined();
    }

    public Client(String id, String firstName, String lastName, String origin, Date joined) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.origin = origin;
        this.joined = joined;
    }

    public String getId() {
        return this.id;
    }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getOrigin() { return this.origin; }
    public Date getJoined() { return this.joined; }

    public void setSocket(Socket socket) throws IOException {
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        RestServer.log("Client logged in with id: " + this.id);
    }

    public boolean isLoggedIn() {
        return this.outputStream != null;
    }

    public void sendMessage(JSONObject content) {
        try {
            this.outputStream.writeUTF(content.toString());
        } catch (Exception e) {
            System.err.println("Could not send message to client with id: " + this.id);
        }
    }

    public void logout() {
        this.outputStream = null;
        RestServer.log("Client logged out with id: " + this.id);
    }
}
