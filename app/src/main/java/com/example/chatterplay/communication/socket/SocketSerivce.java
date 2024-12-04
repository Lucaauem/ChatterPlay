package com.example.chatterplay.communication.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketSerivce extends Thread {
    private static int PORT;

    public SocketSerivce(int port) {
        PORT = port;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("192.168.56.1", PORT)){
            super.run();

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("000000");

            Receiver receiver = new Receiver(new DataInputStream(socket.getInputStream()));
            receiver.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
