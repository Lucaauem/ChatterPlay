package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.client.Client;
import server.client.ClientManager;

public class Receiver extends Thread {
    private final ServerSocket socket;

    public Receiver() throws IOException {
        this.socket = new ServerSocket(RestServer.SOCKET_PORT);
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                Socket clientSocket = this.socket.accept();
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                String loginId = input.readUTF();

                Client client = ClientManager.getInstance().getClient(loginId);

                if(client == null) {
                    output.writeUTF("ERROR");
                    return;
                }

                client.setSocket(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR ON RECEIVER");
        }
    }
}
