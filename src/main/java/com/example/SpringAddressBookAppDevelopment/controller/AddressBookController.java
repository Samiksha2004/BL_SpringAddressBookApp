package com.example.SpringAddressBookAppDevelopment.controller;

import com.example.SpringAddressBookAppDevelopment.model.Contact;
import com.example.SpringAddressBookAppDevelopment.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService; // Service Layer Dependency Injection

    // GET - Retrieve all contacts
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        return new ResponseEntity<>(addressBookService.getAllContacts(), HttpStatus.OK);
    }

    // GET - Retrieve a contact by ID
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable int id) {
        Contact contact = addressBookService.getContactById(id);
        if (contact != null) {
            return new ResponseEntity<>(contact, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // POST - Add a new contact
    @PostMapping("/contacts")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        Contact newContact = addressBookService.addContact(contact);
        return new ResponseEntity<>(newContact, HttpStatus.CREATED);
    }

    // PUT - Update contact by ID
    @PutMapping("/contacts/{id}")
    public ResponseEntity<String> updateContact(@PathVariable int id, @RequestBody Contact contact) {
        if (addressBookService.updateContact(id, contact)) {
            return new ResponseEntity<>("Contact updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Contact not found", HttpStatus.NOT_FOUND);
    }

    // DELETE - Remove a contact by ID
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable int id) {
        if (addressBookService.deleteContact(id)) {
            return new ResponseEntity<>("Contact deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Contact not found", HttpStatus.NOT_FOUND);
    }
}
