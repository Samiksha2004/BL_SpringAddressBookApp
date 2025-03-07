package com.example.SpringAddressBookAppDevelopment.service;

import com.example.SpringAddressBookAppDevelopment.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressBookService {

    private final List<Contact> contacts = new ArrayList<>();

    // Get all contacts
    public List<Contact> getAllContacts() {
        return contacts;
    }

    // Get contact by ID
    public Contact getContactById(int id) {
        if (id >= 0 && id < contacts.size()) {
            return contacts.get(id);
        }
        return null;
    }

    // Add a new contact
    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    // Update existing contact
    public boolean updateContact(int id, Contact contact) {
        if (id >= 0 && id < contacts.size()) {
            contacts.set(id, contact);
            return true;
        }
        return false;
    }

    // Delete a contact
    public boolean deleteContact(int id) {
        if (id >= 0 && id < contacts.size()) {
            contacts.remove(id);
            return true;
        }
        return false;
    }
}
