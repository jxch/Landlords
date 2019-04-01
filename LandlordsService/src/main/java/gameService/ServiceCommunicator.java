package gameService;

import thread.ClientThread;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServiceCommunicator implements Runnable {
    private ServerSocket serverSocket;
    private boolean listening = true;

    ServiceCommunicator(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void listening() throws IOException {
        while (listening) {
            Socket aClient = serverSocket.accept();

            ClientThread clientThread = new ClientThread(aClient);
            Thread thread = new Thread(clientThread);
            thread.start();
        }
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    @Override
    public void run() {
        try {
            listening();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ServiceCommunicator{" +
                "serverSocket=" + serverSocket +
                '}';
    }
}
