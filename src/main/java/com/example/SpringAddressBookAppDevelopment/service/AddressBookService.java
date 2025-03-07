package com.example.SpringAddressBookAppDevelopment.service;

import com.example.SpringAddressBookAppDevelopment.dto.ContactDTO;
import com.example.SpringAddressBookAppDevelopment.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressBookService {
    private List<Contact> contacts = new ArrayList<>();
    private Long nextId = 1L;

    // Method to get all contacts
    public List<Contact> getAllContacts() {
        return contacts;
    }

    // Method to get a contact by ID
    public Contact getContactById(Long id) {
        return contacts.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Method to add a new contact
    public Contact addContact(ContactDTO contactDTO) {
        Contact newContact = new Contact(nextId++, contactDTO.getName(), contactDTO.getPhone(), contactDTO.getEmail());
        contacts.add(newContact);
        return newContact;
    }

    // Method to update a contact
    public boolean updateContact(Long id, ContactDTO contactDTO) {
        Contact contact = getContactById(id);
        if (contact != null) {
            contact.setName(contactDTO.getName());
            contact.setPhone(contactDTO.getPhone());
            contact.setEmail(contactDTO.getEmail());
            return true;
        }
        return false;
    }

    // Method to delete a contact
    public boolean deleteContact(Long id) {
        return contacts.removeIf(contact -> contact.getId().equals(id));
    }
}
