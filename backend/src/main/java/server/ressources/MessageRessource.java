package server.ressources;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import java.io.IOException;
import server.chatroom.ChatroomManager;
import server.database.DatabaseHandler;
import server.message.Message;

public class MessageRessource extends ServerResource {
    @Get
    public String[] getMessages() {
        String chatId = getQuery().getValues("chat");
        Message[] messages = DatabaseHandler.getInstance().getMessages(chatId);
        String[] messageIds = new String[messages.length];

        for(int i=0; i< messages.length; i++) {
            messageIds[i] = messages[i].getId();
        }
        return messageIds;
    }

    @Post
    public void sendMessage(Representation body) throws IOException, JSONException {
        JSONObject json = new JSONObject(body.getText());
        String chatId = json.getString("chatId");
        String senderId = json.getString("senderId");
        String content = json.getString("content");

        DatabaseHandler.getInstance().addMessage(new Message(DatabaseHandler.generateId(Message.ID_LENGTH), chatId, senderId, content));
        ChatroomManager.getInstance().getChatroom(chatId).sendMessage(content);
    }
}
