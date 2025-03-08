package com.example.SpringAddressBookAppDevelopment.controller;

import com.example.SpringAddressBookAppDevelopment.model.Contact;
import com.example.SpringAddressBookAppDevelopment.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressbook")
@Slf4j
public class AddressBookController {

    @Autowired
    AddressBookService addressBookService;

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        log.info("Fetching all contacts");
        return new ResponseEntity<>(addressBookService.getAllContacts(), HttpStatus.OK);
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable int id) {
        log.info("Fetching contact with ID: {}", id);
        Contact contact = addressBookService.getContactById(id);
        return (contact != null)
                ? new ResponseEntity<>(contact, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/contacts")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        log.info("Adding new contact: {}", contact);
        Contact newContact = addressBookService.addContact(contact);
        return new ResponseEntity<>(newContact, HttpStatus.CREATED);
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<String> updateContact(@PathVariable int id, @RequestBody Contact contact) {
        log.info("Updating contact with ID: {}", id);
        return (addressBookService.updateContact(id, contact))
                ? new ResponseEntity<>("Contact updated successfully", HttpStatus.OK)
                : new ResponseEntity<>("Contact not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable int id) {
        log.info("Deleting contact with ID: {}", id);
        return (addressBookService.deleteContact(id))
                ? new ResponseEntity<>("Contact deleted successfully", HttpStatus.OK)
                : new ResponseEntity<>("Contact not found", HttpStatus.NOT_FOUND);
    }
}
