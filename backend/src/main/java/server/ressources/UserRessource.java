package server.ressources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.io.IOException;
import server.RestServer;
import server.client.Client;
import server.client.ClientManager;
import server.database.DatabaseHandler;

public class UserRessource extends ServerResource {
    @Get
    public String user() throws JSONException {
        String clientId = getQuery().getValues("id");
        Client client = DatabaseHandler.getInstance().getClient(clientId);

        if(client == null) {
            return null;
        }

        JSONObject json = new JSONObject();
        json.put("id", client.getId());
        json.put("firstName", client.getFirstName());
        json.put("lastName", client.getLastName());
        json.put("origin", client.getOrigin());
        json.put("joined", client.getJoined());

        return json.toString();
    }

    @Post
    public int login(Representation body) throws IOException, JSONException {
        JSONObject json = new JSONObject(body.getText());
        String userId = json.getString("id");

        // !TODO! Login process with name and password

        Client client = new Client(userId);
        ClientManager.getInstance().addClient(client);

        return RestServer.SOCKET_PORT;
    }

    @Delete
    public void removeUser() {
        String clientId = getQuery().getValues("id");
        ClientManager.getInstance().removeClient(clientId);
    }
}
