package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient {
    private final int id;
    private final Socket socket;

    public ChatClient(int id, Socket socket) {
        this.id = id;
        this.socket = socket;
    }

    public int getId() {
        return id;
    }

    public void sendMessage(String message) throws IOException {
        DataOutputStream outputStream = new DataOutputStream(this.socket.getOutputStream());
        outputStream.writeUTF(message);
    }
}
