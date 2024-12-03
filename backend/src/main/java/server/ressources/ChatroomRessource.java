package server.ressources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import java.io.IOException;
import server.RestServer;
import server.chatroom.Chatroom;
import server.chatroom.ChatroomManager;
import server.database.DatabaseHandler;

public class ChatroomRessource extends ServerResource {
    @Get
    public String[] getChatrooms() throws JSONException {
        Chatroom[] chatrooms = ChatroomManager.getInstance().getChatrooms();

        JSONObject json = new JSONObject();

        for (Chatroom chat: chatrooms) {
            json.put(chat.getId(), chat.getName());
        }

        return new String[]{json.toString()};
    }

    @Post
    public void createChatroom(Representation body) throws IOException, JSONException {
        JSONObject json = new JSONObject(body.getText());

        ChatroomManager.getInstance().createChatroom(json.getString("name"));
        RestServer.log("Created chatroom: " + json.getString("name"));
    }

    @Delete
    public void removeChat(Representation body) throws IOException, JSONException {
        JSONObject json = new JSONObject(body.getText());
        String chatId = json.getString("id");

        ChatroomManager.getInstance().removeChatroom(chatId);
        DatabaseHandler.getInstance().removeChatroom(chatId);
    }
}
