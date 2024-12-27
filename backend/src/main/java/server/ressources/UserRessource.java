package server.ressources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import java.io.IOException;
import server.RestServer;
import server.client.Client;
import server.client.ClientManager;
import server.database.DatabaseHandler;

public class UserRessource extends ServerResource {
    @Get
    public boolean user() {
        String clientId = getQuery().getValues("id");
        Client client = DatabaseHandler.getInstance().getClient(clientId);

        return !client.getId().equals("-1");
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
}
