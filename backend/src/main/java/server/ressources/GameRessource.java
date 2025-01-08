package server.ressources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.client.ClientManager;
import server.games.GameHandler;

import java.io.IOException;

public class GameRessource extends ServerResource {
    @Post
    public void createGame(Representation body) throws IOException, JSONException {
        JSONObject jsonBody = new JSONObject(body.getText());
        String oponentId = jsonBody.getString("oponentId");
        String creatorId = jsonBody.getString("creatorId");
        String gameType = jsonBody.getString("gameType");
        String gameId = GameHandler.getInstance().createGame();

        System.out.println(creatorId + " invited " + oponentId + " to " + gameType);

        JSONObject json = new JSONObject();
        json.put("type", "invite");
        json.put("creatorId", creatorId);
        json.put("gameType", gameType);
        json.put("gameId", gameId);

        ClientManager.getInstance().getClient(creatorId).sendMessage(json);
        ClientManager.getInstance().getClient(oponentId).sendMessage(json);
    }
}
