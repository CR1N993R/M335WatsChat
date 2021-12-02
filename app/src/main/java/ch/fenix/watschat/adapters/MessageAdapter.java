package ch.fenix.watschat.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ch.fenix.watschat.R;
import ch.fenix.watschat.models.Message;

public class MessageAdapter extends ArrayAdapter<Message> {
    private Context context;
    private int resource;

    public MessageAdapter(@NonNull Context context, int resource, List<Message> contacts) {
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
        TextView textView = convertView.findViewById(R.id.message);
        textView.setText(getItem(position).getMessage());
        String tel = context.getSharedPreferences("telephone", Context.MODE_PRIVATE).getString("telephone", "");
        if (getItem(position).getSender().equals(tel)) {
            //TODO Hella buggy
            textView.setTextColor(Color.BLUE);
        }
        return convertView;
    }
}
