package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class CommunicationServer {
    private static CommunicationServer instance;
    private final HashMap<Integer, ChatClient> clients = new HashMap<>();
    private final HashMap<Integer, ChatRoom> chatRooms = new HashMap<>();

    private CommunicationServer() {}

    public static CommunicationServer getInstance() {
        if(instance == null) {
            instance = new CommunicationServer();
        }
        return instance;
    }

    public void addChatRoom(ChatRoom chatRoom) {
        this.chatRooms.put(chatRoom.getId(), chatRoom);
    }

    private void addClient(ChatClient client) {
        try {
            this.clients.put(client.getId(), client);
            System.out.println("LOGGED IN CLIENT WITH WITH " + client.getId());

            // !TODO! Add to all chatrooms (get ids from db)
        } catch (Exception e) {
            System.err.println("COULD NOT LOG IN CLIENT WITH ID " + client.getId());
        }
    }

    private void sendMessage(int chatId, String message) {
        try {
            this.chatRooms.get(chatId).sendMessage(message);
        } catch (Exception e) {
            System.err.println("COULD NOT SEND MESSAGE TO CHAT WITH ID " + chatId);
        }
    }

    public void onCommand(Command command, String[] content, Socket client) {
        switch (command) {
            // [LOGIN;CLIENT_ID]
            case LOGIN -> {
                ChatClient chatClient = new ChatClient(Integer.parseInt(content[0]), client);
                this.addClient(chatClient);
            }
            // [SEND;CHAT_ID;MESSAGE]
            case SEND -> {
                int targetId = Integer.parseInt(content[0]);
                this.sendMessage(targetId, content[1]);
            }
            // [JOIN;CLIENT_ID;CHAT_ID;]
            case JOIN -> {
                int chatId = Integer.parseInt(content[1]);
                int clientId = Integer.parseInt(content[0]);

                this.chatRooms.get(chatId).addClient(this.clients.get(clientId));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("ONLINE");

        // !TODO! Load chatrooms from db
        CommunicationServer.getInstance().addChatRoom(new ChatRoom(0));
        CommunicationServer.getInstance().addChatRoom(new ChatRoom(1));

        while (true) {
            // !TODO! Send data in better format (e.g. JSON)
            // !TODO! Start as thread
            Socket clientSocket = serverSocket.accept();

            Receiver receiver = new Receiver(clientSocket);
            receiver.start();
        }
    }
}