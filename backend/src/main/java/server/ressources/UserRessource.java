package server.ressources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import java.io.IOException;
import java.sql.Date;
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
            JSONObject json = new JSONObject();
            json.put("id", "-1");
            json.put("firstName", "");
            json.put("lastName", "");
            json.put("origin", "");
            json.put("joined", "");

            return json.toString();
        }

        JSONObject json = new JSONObject();
        json.put("id", client.getId());
        json.put("firstName", client.getFirstName());
        json.put("lastName", client.getLastName());
        json.put("origin", client.getOrigin());
        json.put("joined", client.getJoined().toString());

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

    @Put
    public void update(Representation body) throws IOException, JSONException {
        JSONObject json = new JSONObject(body.getText());
        String userId = json.getString("id");

        Client client = new Client(userId,
                json.getString("firstName"),
                json.getString("lastName"),
                json.getString("origin"),
                new Date(0) // Does not get updated
        );

        RestServer.log("Updated user with id " + userId + ": " + json.getString("firstName") + ", " + json.getString("lastName") + ", " + json.getString("origin"));

        ClientManager.getInstance().updateClient(client);
    }

    @Delete
    public void logout() {
        String clientId = getQuery().getValues("id");
        ClientManager.getInstance().getClient(clientId).logout();
    }
}
