package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
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

    public void addClient(ChatClient client) {
        try {
            this.clients.put(client.getId(), client);
            client.sendMessage("LOGGED IN");
        } catch (Exception e) {
            System.out.println("COULD NOT LOG IN CLIENT WITH ID " + client.getId());
        }
    }

    public void sendMessage(int clientId, String message) throws IOException {
        this.clients.get(clientId).sendMessage(message);
    }

    private void onCommand(Command command, String[] content, Socket client) {
        switch (command) {
            case LOGIN -> {
                ChatClient chatClient = new ChatClient(Integer.parseInt(content[0]), client);
                this.addClient(chatClient);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        CommunicationServer communicationServer = CommunicationServer.getInstance();
        ServerSocket serverSocket = new ServerSocket(8080);

        System.out.println("ONLINE");
        // !TODO! Send data in better format (e.g. JSON)
        // !TODO! Start as thread
        Socket clientSocket = serverSocket.accept();

        while (true) {
            // !TODO! Start as thread
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());

            String message = inputStream.readUTF();
            String[] messageContent = message.split(";");
            Command command = Command.valueOf(messageContent[0]);

            communicationServer.onCommand(command, Arrays.copyOfRange(messageContent, 1, messageContent.length), clientSocket);
        }
    }
}