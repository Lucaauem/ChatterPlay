package server.ressources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import java.io.IOException;

import server.client.Client;
import server.client.ClientManager;

public class User extends ServerResource {
    @Post
    public boolean login(Representation body) throws IOException, JSONException {
        JSONObject json = new JSONObject(body.getText());
        String userId = json.getString("id");

        // !TODO! Login process with name and password

        Client client = new Client(userId);

        ClientManager.getInstance().addClient(client);

        return true;
    }
}
