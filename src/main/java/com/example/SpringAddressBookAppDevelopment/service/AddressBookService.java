package com.example.SpringAddressBookAppDevelopment.service;

import com.example.SpringAddressBookAppDevelopment.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j  // Enable Lombok Logging
@Service
public class AddressBookService {

    List<Contact> contacts = new ArrayList<>(); // In-memory storage

    // Get all contacts
    public List<Contact> getAllContacts() {
        log.info("Fetching all contacts");
        return contacts;
    }

    // Get contact by ID
    public Contact getContactById(int id) {
        log.info("Fetching contact with ID: {}", id);
        if (id >= 0 && id < contacts.size()) {
            return contacts.get(id);
        }
        log.warn("Contact with ID {} not found", id);
        return null;
    }

    // Add a new contact
    public Contact addContact(Contact contact) {
        log.info("Adding new contact: {}", contact);
        contacts.add(contact);
        return contact;
    }

    // Update existing contact
    public boolean updateContact(int id, Contact contact) {
        log.info("Updating contact with ID: {}", id);
        if (id >= 0 && id < contacts.size()) {
            contacts.set(id, contact);
            return true;
        }
        log.warn("Contact with ID {} not found, update failed", id);
        return false;
    }

    // Delete a contact
    public boolean deleteContact(int id) {
        log.info("Deleting contact with ID: {}", id);
        if (id >= 0 && id < contacts.size()) {
            contacts.remove(id);
            return true;
        }
        log.warn("Contact with ID {} not found, deletion failed", id);
        return false;
    }
}
