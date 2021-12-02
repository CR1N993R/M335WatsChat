package ch.fenix.watschat.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import ch.fenix.watschat.R;
import ch.fenix.watschat.adapters.MessageAdapter;
import ch.fenix.watschat.managers.DataManager;
import ch.fenix.watschat.models.Contact;
import ch.fenix.watschat.models.Message;
import ch.fenix.watschat.models.SocketMsg;
import ch.fenix.watschat.services.SocketService;

public class ChatActivity extends AppCompatActivity {
    public static ChatActivity chatActivity;

    private DataManager dataManager;
    private EditText editText;
    private String tel;
    private SocketService socketService;
    private ListView content;
    private MessageAdapter adapter;
    private static List<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatActivity = this;
        tel = getIntent().getExtras().getString("contact");
        dataManager = new DataManager(getApplicationContext());
        content = findViewById(R.id.content);
        messages = dataManager.getMessagesByTel(tel);
        adapter = new MessageAdapter(getApplicationContext(), R.layout.adapter_view_message, messages);
        content.setAdapter(adapter);
        editText = findViewById(R.id.input);
        findViewById(R.id.send).setOnClickListener(e -> sendMessage());
        bindService();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void bindService(){
        Intent intent = new Intent(this, SocketService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void sendMessage() {
        if (editText.getText().toString().length() != 0 && socketService != null) {
            SharedPreferences tel = getApplicationContext().getSharedPreferences("telephone", MODE_PRIVATE);
            Message message = new Message(LocalDateTime.now(), editText.getText().toString(), tel.getString("telephone",""), this.tel);
            dataManager.saveMessage(message);
            editText.setText("");
            refresh();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                socketService.sendMessage(objectMapper.writeValueAsString(new SocketMsg(tel.getString("telephone", ""), message)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateList() {
        runOnUiThread(this::refresh);
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

    private void refresh() {
        messages.clear();
        messages.addAll(dataManager.getMessagesByTel(tel));
        adapter.notifyDataSetChanged();
        content.setSelection(adapter.getCount() - 1);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}