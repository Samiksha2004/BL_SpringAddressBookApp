package com.example.SpringAddressBookAppDevelopment.controller;

import com.example.SpringAddressBookAppDevelopment.dto.ContactDTO;
import com.example.SpringAddressBookAppDevelopment.model.Contact;
import com.example.SpringAddressBookAppDevelopment.service.AddressBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    private final AddressBookService addressBookService;

    public AddressBookController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    // GET - Retrieve all contacts
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        return ResponseEntity.ok(addressBookService.getAllContacts());
    }

    // GET - Retrieve a contact by ID
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Contact contact = addressBookService.getContactById(id);
        return (contact != null) ? ResponseEntity.ok(contact) : ResponseEntity.notFound().build();
    }

    // POST - Add a new contact
    @PostMapping("/contacts")
    public ResponseEntity<Contact> addContact(@RequestBody ContactDTO contactDTO) {
        return ResponseEntity.ok(addressBookService.addContact(contactDTO));
    }

    // PUT - Update contact by ID
    @PutMapping("/contacts/{id}")
    public ResponseEntity<String> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        return addressBookService.updateContact(id, contactDTO) ?
                ResponseEntity.ok("Contact updated successfully") :
                ResponseEntity.notFound().build();
    }

    // DELETE - Remove a contact by ID
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        return addressBookService.deleteContact(id) ?
                ResponseEntity.ok("Contact deleted successfully") :
                ResponseEntity.notFound().build();
    }
}
