package ch.fenix.watschat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import ch.fenix.watschat.R;
import ch.fenix.watschat.adapters.ContactsAdapter;
import ch.fenix.watschat.managers.DataManager;
import ch.fenix.watschat.models.Contact;

public class ContactsActivity extends AppCompatActivity {
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataManager = new DataManager(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ((ListView) findViewById(R.id.contacts_content))
                .setAdapter(new ContactsAdapter(this, R.layout.adapter_view_contacts, dataManager.getContacts()));
        ((ListView) findViewById(R.id.contacts_content)).setOnItemClickListener((parent, view, position, id) -> {
            loadEditContact((Contact) parent.getItemAtPosition(position));
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void loadEditContact(Contact contact) {
        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra("telephone", contact.getTel());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contacts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.create_contact) {
            Intent intent = new Intent(this, CreateContactActivity.class);
            startActivity(intent);
            return super.onOptionsItemSelected(item);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}