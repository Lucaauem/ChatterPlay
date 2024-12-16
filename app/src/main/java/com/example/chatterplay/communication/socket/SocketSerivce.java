package com.example.chatterplay.communication.socket;

import com.example.chatterplay.UserSession;

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
        try (Socket socket = new Socket("172.26.144.1", PORT)){
            super.run();

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(UserSession.Companion.getInstance().getUser().getId());

            Receiver receiver = new Receiver(new DataInputStream(socket.getInputStream()));
            receiver.start();

            while(true) {}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
