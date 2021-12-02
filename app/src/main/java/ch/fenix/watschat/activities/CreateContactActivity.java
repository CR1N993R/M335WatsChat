package ch.fenix.watschat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import ch.fenix.watschat.R;
import ch.fenix.watschat.managers.DataManager;
import ch.fenix.watschat.models.Contact;

public class CreateContactActivity extends AppCompatActivity {
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataManager = new DataManager(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        findViewById(R.id.btn_contact).setOnClickListener(e -> createContact());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void createContact() {
        String tel = ((EditText)findViewById(R.id.telephone)).getText().toString();
        String username = ((EditText) findViewById(R.id.name)).getText().toString();
        if (tel.length() != 0 && username.length() != 0) {
            dataManager.saveContact(new Contact(username, tel));
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}