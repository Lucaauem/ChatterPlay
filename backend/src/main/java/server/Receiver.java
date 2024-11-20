package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
                String message = new String(inputStream.readAllBytes()).trim();

                if(message.isEmpty()) {
                    return;
                }

                String[] messageContent = message.split(";");
                System.out.println(">>>> " + message);
                Command command = Command.valueOf(messageContent[0]);

                CommunicationServer.getInstance().onCommand(command, Arrays.copyOfRange(messageContent, 1, messageContent.length), this.socket);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERROR ON RECEIVER");
                this.isRunning = false;
            }
        }
    }

    public Receiver(Socket socket) {
        this.socket = socket;
    }
}
