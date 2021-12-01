package ch.fenix.watschat.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

import ch.fenix.watschat.R;
import ch.fenix.watschat.adapters.MessageAdapter;
import ch.fenix.watschat.managers.DataManager;
import ch.fenix.watschat.models.Contact;
import ch.fenix.watschat.models.Message;
import ch.fenix.watschat.models.SocketMsg;
import ch.fenix.watschat.services.SocketService;

public class ChatActivity extends AppCompatActivity {
    private DataManager dataManager;
    private EditText editText;
    private String tel;
    private SocketService socketService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tel = getIntent().getExtras().getString("contact");
        dataManager = new DataManager(getApplicationContext());
        ListView content = findViewById(R.id.content);
        content.setAdapter(new MessageAdapter(getApplicationContext(), R.layout.adapter_view_message, dataManager.getMessagesByTel(tel)));
        editText = findViewById(R.id.input);
        findViewById(R.id.send).setOnClickListener(e -> sendMessage());
        bindService();
    }

    private void bindService(){
        Intent intent = new Intent(this, SocketService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void sendMessage() {
        if (editText.getText().toString().length() != 0 && socketService != null) {
            SharedPreferences tel = getApplicationContext().getSharedPreferences("telephone", MODE_PRIVATE);
            Message message = new Message(LocalDateTime.now(), editText.getText().toString(), tel.getString("telephone",""), this.tel);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                socketService.sendMessage(objectMapper.writeValueAsString(new SocketMsg(tel.getString("telephone", ""), message)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            SocketService.LocalBinder binder = (SocketService.LocalBinder) service;
            socketService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };
}