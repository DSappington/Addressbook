package pojo;

import data.ContactFileHandler;

import java.util.ArrayList;
import java.util.List;

public class AddressbookHandler {
    private List<Contact> contacts;
    private ContactFileHandler contactFileHandler;



    public AddressbookHandler() {
        contacts = new ArrayList<>();
        contactFileHandler = new ContactFileHandler();
        contacts.addAll(contactFileHandler.loadContacts("contacts.con"));
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void deleteContact(Contact contact) {
        contacts.remove(contact);
    }

    public void updateContact(Contact oldContact, Contact newContact) {
        int index = contacts.indexOf(oldContact);
        if (index != -1) {
            contacts.set(index, newContact);
        }
    }

    public void saveContactsToFile(String fileName) {
        contactFileHandler = new ContactFileHandler();
        contactFileHandler.saveContacts(contacts, fileName);
    }
}
