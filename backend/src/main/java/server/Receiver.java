package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class Receiver extends Thread {
    private final Socket socket;
    private boolean isRunning = true;

    @Override
    public void run() {
        super.run();

        while (this.isRunning) {
            try {
                DataInputStream inputStream = new DataInputStream(this.socket.getInputStream());
                String message = inputStream.readUTF();
                String[] messageContent = message.split(";");
                Command command = Command.valueOf(messageContent[0]);

                CommunicationServer.getInstance().onCommand(command, Arrays.copyOfRange(messageContent, 1, messageContent.length), this.socket);
            } catch (IOException e) {
                System.out.println("ERROR ON RECEIVER");
                this.isRunning = false;
            }
        }
    }

    public Receiver(Socket socket) {
        this.socket = socket;
    }
}
