package ch.fenix.watschat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ch.fenix.watschat.R;
import ch.fenix.watschat.adapters.ChatsAdapter;
import ch.fenix.watschat.managers.DataManager;
import ch.fenix.watschat.models.Contact;
import ch.fenix.watschat.services.SocketService;

public class MainActivity extends AppCompatActivity {
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String tel = getApplicationContext().getSharedPreferences("telephone", MODE_PRIVATE).getString("telephone", "");
        if (tel.equals("")) {
            showDialog();
        }
        dataManager = new DataManager(getApplicationContext());
        startService(new Intent(getApplicationContext(), SocketService.class));
        ((ListView) findViewById(R.id.chats)).setAdapter(new ChatsAdapter(this, R.layout.adapter_view_chats, dataManager.getContacts()));
        ((ListView) findViewById(R.id.chats)).setOnItemClickListener((parent, view, position, id) -> {
            loadChat((Contact) parent.getItemAtPosition(position));
        });
    }

    private void loadChat(Contact contact) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("contact", contact.getTel());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        EditText editText = dialog.findViewById(R.id.enter_phone);
        Button button = dialog.findViewById(R.id.button_set_phone);
        button.setOnClickListener(e ->{
            if (editText.getText().toString().length() != 0) {
                SharedPreferences sp = getApplicationContext().getSharedPreferences("telephone", MODE_PRIVATE);
                sp.edit().putString("telephone", editText.getText().toString()).apply();
                dialog.cancel();
            }
        });
        dialog.show();
    }
}