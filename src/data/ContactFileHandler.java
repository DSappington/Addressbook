package data;

import pojo.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactFileHandler {
    @SuppressWarnings("unchecked")
	public List<Contact> loadContacts(String fileName) {
        List<Contact> contacts = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    public void saveContacts(List<Contact> contacts, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
