package ch.fenix.watschat.threads;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ch.fenix.watschat.activities.ChatActivity;
import ch.fenix.watschat.models.SocketMsg;
import ch.fenix.watschat.managers.DataManager;

public class SocketThread extends Thread {
    private DataManager dataManager;
    private Context context;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private boolean running;
    private List<String> messagesToSend = new ArrayList<>();

    public SocketThread(Context context) {
        this.context = context;
        this.dataManager = new DataManager(context);
    }

    @Override
    public void run() {
        try {
            socket = new Socket("192.168.187.47", 25555);
            writer = new PrintWriter(socket.getOutputStream(),true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            running = true;
            listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (socket.isConnected()) {
            writer.println(message);
        }else {
            messagesToSend.add(message);
        }
    }

    public void listen() throws IOException {
        while (running) {
            parseMessage(reader.readLine());
        }
    }

    private void parseMessage(String message) throws IOException {
        if (message != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            SocketMsg msg = objectMapper.readValue(message, SocketMsg.class);
            if (msg.getMessage() != null) {
                dataManager.saveMessage(msg.getMessage());
                ChatActivity.chatActivity.updateList();
            }
            if (msg.getMessages() != null) {
                dataManager.saveMessages(msg.getMessages());
            }
        }
    }
}
