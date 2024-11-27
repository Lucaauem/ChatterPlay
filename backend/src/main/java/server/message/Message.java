package server.message;

import server.chatroom.Chatroom;
import server.chatroom.ChatroomManager;
import server.client.Client;
import server.client.ClientManager;

public class Message {
    public static final int ID_LENGTH = 6;
    private final String id;
    private final Chatroom chat;
    private final Client sender;
    private final String content;

    public Message(String id, String chatId, String senderId, String content) {
        this.id = id;
        this.chat = ChatroomManager.getInstance().getChatroom(chatId);
        this.sender = ClientManager.getInstance().getClient(senderId);
        this.content = content;
    }

    public String getId() {
        return this.id;
    }

    public Chatroom getChat() {
        return this.chat;
    }

    public Client getSender() {
        return this.sender;
    }

    public String getContent() {
        return this.content;
    }
}
