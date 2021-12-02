package ch.fenix.watschat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ch.fenix.watschat.R;
import ch.fenix.watschat.managers.DataManager;
import ch.fenix.watschat.models.Contact;

public class EditContactActivity extends AppCompatActivity {
    private DataManager dataManager;
    private EditText name;
    private EditText tel;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataManager = new DataManager(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        String tel = getIntent().getExtras().getString("telephone");
        contact = dataManager.getContactByTel(tel);
        this.name = findViewById(R.id.edit_contact_name);
        this.tel = findViewById(R.id.edit_contact_tel);
        name.setText(contact.getName());
        this.tel.setText(contact.getTel());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ((Button) findViewById(R.id.btn_delete_contact)).setOnClickListener(e -> deleteMessage());

        ((Button) findViewById(R.id.btn_save_contact)).setOnClickListener(e -> saveMessage());
    }

    private void saveMessage() {
        if (name.getText().length() > 0 && tel.getText().length() > 0) {
            contact.setName(name.getText().toString());
            contact.setTel(tel.getText().toString());
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        }
    }

    private void deleteMessage() {
        dataManager.deleteContact(contact);
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}