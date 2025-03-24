package com.example.addressbook.controller;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBookEntry;
import com.example.addressbook.service.IAddressBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addressbook")
@Tag(name = "Address Book API", description = "CRUD operations for Address Book entries")
public class AddressBookController {

    @Autowired
    private IAddressBookService addressBookService;

    @Operation(summary = "Get all contacts", description = "Fetch all contacts from address book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    @GetMapping
    public List<AddressBookEntry> getAllContacts() {
        return addressBookService.getAllContacts();
    }

    @Operation(summary = "Get contact by ID", description = "Fetch a contact by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact found"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @GetMapping("/{id}")
    public AddressBookEntry getContactById(@PathVariable int id) {
        return addressBookService.getContactById(id);
    }

    @Operation(summary = "Add a new contact", description = "Add a new contact to the address book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact created successfully")
    })
    @PostMapping
    public AddressBookEntry addContact(@RequestBody AddressBookDTO addressBookDTO) {
        return addressBookService.addContact(addressBookDTO);
    }

    @Operation(summary = "Update a contact", description = "Update an existing contact by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact updated successfully"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @PutMapping("/{id}")
    public AddressBookEntry updateContact(@PathVariable int id, @RequestBody AddressBookDTO addressBookDTO) {
        return addressBookService.updateContact(id, addressBookDTO);
    }

    @Operation(summary = "Delete a contact", description = "Delete a contact by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable int id) {
        addressBookService.deleteContact(id);
        return "Contact deleted successfully!";
    }
}
