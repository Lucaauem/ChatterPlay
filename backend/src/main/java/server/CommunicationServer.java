package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class CommunicationServer {
    private static CommunicationServer instance;
    private final HashMap<Integer, ChatClient> clients = new HashMap<>();

    private CommunicationServer() {}

    public static CommunicationServer getInstance() {
        if(instance == null) {
            instance = new CommunicationServer();
        }
        return instance;
    }

    private void addClient(ChatClient client) {
        try {
            this.clients.put(client.getId(), client);
            System.out.println("LOGGED IN CLIENT WITH WITH " + client.getId());
        } catch (Exception e) {
            System.err.println("COULD NOT LOG IN CLIENT WITH ID " + client.getId());
        }
    }

    private void sendMessage(int clientId, String message) {
        try {
            this.clients.get(clientId).sendMessage(message);
        } catch (Exception e) {
            System.err.println("COULD NOT SEND MESSAGE TO CLIENT WITH ID " + clientId);
        }
    }

    public void onCommand(Command command, String[] content, Socket client) {
        switch (command) {
            // [LOGIN;ID]
            case LOGIN -> {
                ChatClient chatClient = new ChatClient(Integer.parseInt(content[0]), client);
                this.addClient(chatClient);
            }
            // [SEND;FROM;TO;MESSAGE]
            case SEND -> {
                int targetId = Integer.parseInt(content[0]);
                this.sendMessage(targetId, content[1]);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("ONLINE");

        while (true) {
            // !TODO! Send data in better format (e.g. JSON)
            // !TODO! Start as thread
            Socket clientSocket = serverSocket.accept();

            Receiver receiver = new Receiver(clientSocket);
            receiver.start();
        }
    }
}