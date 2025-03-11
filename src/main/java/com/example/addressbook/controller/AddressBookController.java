package com.example.addressbook.controller;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBookEntry;
import com.example.addressbook.service.IAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {

    @Autowired
    private IAddressBookService addressBookService;

    @GetMapping
    public List<AddressBookEntry> getAllContacts() {
        return addressBookService.getAllContacts();
    }

    @GetMapping("/{id}")
    public AddressBookEntry getContactById(@PathVariable int id) {
        return addressBookService.getContactById(id);
    }

    @PostMapping
    public AddressBookEntry addContact(@RequestBody AddressBookDTO addressBookDTO) {
        return addressBookService.addContact(addressBookDTO);
    }

    @PutMapping("/{id}")
    public AddressBookEntry updateContact(@PathVariable int id, @RequestBody AddressBookDTO addressBookDTO) {
        return addressBookService.updateContact(id, addressBookDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable int id) {
        addressBookService.deleteContact(id);
        return "Contact deleted successfully!";
    }
}
