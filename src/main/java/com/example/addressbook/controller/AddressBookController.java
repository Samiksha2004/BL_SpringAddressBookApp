package com.example.addressbook.controller;

import com.example.addressbook.model.AddressBookEntry;
import com.example.addressbook.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    @Autowired
    private AddressBookService service;

    @PostMapping
    public ResponseEntity<AddressBookEntry> createEntry(@RequestBody AddressBookEntry entry) {
        return ResponseEntity.ok(service.addEntry(entry));
    }

    @GetMapping
    public ResponseEntity<List<AddressBookEntry>> getAllEntries() {
        return ResponseEntity.ok(service.getAllEntries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBookEntry> getEntry(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEntryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressBookEntry> updateEntry(@PathVariable Long id, @RequestBody AddressBookEntry entry) {
        AddressBookEntry updatedEntry = service.updateEntry(id, entry);
        return updatedEntry != null ? ResponseEntity.ok(updatedEntry) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable Long id) {
        service.deleteEntry(id);
        return ResponseEntity.ok("Entry deleted successfully");
    }
}
