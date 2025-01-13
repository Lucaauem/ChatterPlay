package server.games;

import org.json.JSONException;
import org.json.JSONObject;
import server.client.Client;
import server.client.ClientManager;

public class GameInstance {
    private final Client client1;
    private final Client client2;

    public GameInstance(GameType type, String creatorId, String oponentId) {
        this.client1 = ClientManager.getInstance().getClient(creatorId);
        this.client2 = ClientManager.getInstance().getClient(oponentId);
    }

    public void makeTurn(String move, Integer playerId) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("type", "gameMove");
        data.put("move", move);
        data.put("playerId", playerId);

        if(playerId == 0) {
            client2.sendMessage(data);
        } else if (playerId == 1) {
            client1.sendMessage(data);
        }
    }
}
