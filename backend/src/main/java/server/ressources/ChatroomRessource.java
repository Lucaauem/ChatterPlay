package server.ressources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.io.IOException;
import server.RestServer;
import server.chatroom.Chatroom;
import server.chatroom.ChatroomManager;
import server.client.Client;
import server.client.ClientManager;
import server.database.DatabaseHandler;

public class ChatroomRessource extends ServerResource {
    @Get
    public String[] getChatrooms() throws JSONException {
        String userId = getQuery().getValues("userId");

        Chatroom[] chatrooms = ChatroomManager.getInstance().getChatrooms(userId);

        JSONObject json = new JSONObject();

        for (Chatroom chat: chatrooms) {
            json.put(chat.getId(), chat.getName());
        }

        return new String[]{json.toString()};
    }

    @Put
    public boolean joinChatroom(Representation entity) throws JSONException {
        String userId = getQuery().getValues("user");
        String chatroomId = getQuery().getValues("chatroom");

        if(!ChatroomManager.getInstance().chatroomExists(chatroomId)) {
            return false;
        }

        DatabaseHandler.getInstance().addClientToChat(chatroomId, userId);
        Client client = ClientManager.getInstance().getClient(userId);
        ChatroomManager.getInstance().getChatroom(chatroomId).addClient(client);

        return true;
    }

    @Post
    public void createChatroom(Representation body) throws IOException, JSONException {
        JSONObject json = new JSONObject(body.getText());

        ChatroomManager.getInstance().createChatroom(json.getString("name"), json.getString("creator"));
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
