package com.example.SpringAddressBookAppDevelopment.controller;

import com.example.SpringAddressBookAppDevelopment.model.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    private List<Contact> contacts = new ArrayList<>();

    // GET - Retrieve all contacts
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    // GET - Retrieve a contact by ID
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable int id) {
        if (id >= 0 && id < contacts.size()) {
            return new ResponseEntity<>(contacts.get(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // POST - Add a new contact
    @PostMapping("/contacts")
    public ResponseEntity<String> addContact(@RequestBody Contact contact) {
        contacts.add(contact);
        return new ResponseEntity<>("Contact added successfully", HttpStatus.CREATED);
    }

    // PUT - Update contact by ID
    @PutMapping("/contacts/{id}")
    public ResponseEntity<String> updateContact(@PathVariable int id, @RequestBody Contact contact) {
        if (id >= 0 && id < contacts.size()) {
            contacts.set(id, contact);
            return new ResponseEntity<>("Contact updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE - Remove a contact by ID
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable int id) {
        if (id >= 0 && id < contacts.size()) {
            contacts.remove(id);
            return new ResponseEntity<>("Contact deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
