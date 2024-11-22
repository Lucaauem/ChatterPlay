package server.ressources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.Chatroom;
import server.database.DatabaseHandler;

public class ChatroomRessource extends ServerResource {
    @Get
    public String getChatrooms() throws JSONException {
        String userId = (String) getRequest().getAttributes().get("userId");
        Chatroom[] chatrooms = DatabaseHandler.getInstance().getChatsByUserid(userId);

        JSONObject json = new JSONObject();

        for (Chatroom chat: chatrooms) {
            json.put(chat.getId(), chat.getName());
        }

        return json.toString();
    }
}
