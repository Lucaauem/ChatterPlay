package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.client.Client;
import server.client.ClientManager;

public class Receiver extends Thread {
    private ServerSocket socket;

    public Receiver() throws IOException {
        this.socket = new ServerSocket(8081);
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
                    output.writeUTF("403");
                    return;
                }

                client.setSocket(clientSocket);
                output.writeUTF("200");
            }
        } catch (IOException e) {
            System.out.println("ERROR ON RECEIVER");
        }
    }
}
