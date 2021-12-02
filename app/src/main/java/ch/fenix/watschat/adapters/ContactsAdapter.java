package ch.fenix.watschat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ch.fenix.watschat.R;
import ch.fenix.watschat.models.Contact;

public class ContactsAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resource;

    public ContactsAdapter(@NonNull Context context, int resource, List<Contact> contacts) {
        super(context, resource, contacts);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.contact_name)).setText(getItem(position).getName());
        ((TextView) convertView.findViewById(R.id.contact_tel)).setText(getItem(position).getTel());
        return convertView;
    }
}
