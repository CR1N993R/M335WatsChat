package ch.fenix.watschat.managers;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import ch.fenix.watschat.models.Contact;
import ch.fenix.watschat.models.Message;
import lombok.Getter;


public class DataManager {
    private Context context;
    private ObjectMapper mapper;
    @Getter
    private static List<Contact> contacts;
    @Getter
    private static List<Message> messages;

    public DataManager(Context context) {
        mapper = new ObjectMapper();
        try {
            this.context = context;
            if (contacts == null || messages == null) {
                String data = FileManager.getDataFromFile(context, "messages.json");
                messages = mapper.readValue(data, new TypeReference<List<Message>>() {
                });
                data = FileManager.getDataFromFile(context, "contacts.json");
                contacts = mapper.readValue(data, new TypeReference<List<Contact>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveContact(Contact contact) {
        if (!contacts.contains(contact)) {
            contacts.add(contact);
        }
        saveContactsToStorage();
    }

    public void deleteContact(Contact contact) {
        contacts.remove(contact);
        saveContactsToStorage();
    }

    private void saveContactsToStorage() {
        try {
            FileManager.saveDataToFile(context, "contacts.json", mapper.writeValueAsString(contacts));
        } catch (IOException e) {
        }
    }

    public void saveMessages(List<Message> messages) {
        DataManager.messages.addAll(messages);
        saveMessagesToStorage();
    }

    public void saveMessage(Message message) {
        messages.add(message);
        saveContactsToStorage();
    }

    private void saveMessagesToStorage() {
        try {
            FileManager.saveDataToFile(context, "messages.json", mapper.writeValueAsString(messages));
        } catch (IOException e) {
        }
    }
}
