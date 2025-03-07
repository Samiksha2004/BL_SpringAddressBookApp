package com.example.SpringAddressBookAppDevelopment.controller;

import com.example.SpringAddressBookAppDevelopment.dto.AddressBookDTO;
import com.example.SpringAddressBookAppDevelopment.model.AddressBookEntry;
import com.example.SpringAddressBookAppDevelopment.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {

    @Autowired
    private AddressBookService service;

    @GetMapping
    public List<AddressBookEntry> getAllEntries() {
        return service.getAllEntries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBookEntry> getEntryById(@PathVariable Long id) {
        Optional<AddressBookEntry> entry = service.getEntryById(id);
        return entry.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AddressBookEntry addEntry(@RequestBody AddressBookDTO dto) {
        return service.addEntry(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressBookEntry> updateEntry(@PathVariable Long id, @RequestBody AddressBookDTO dto) {
        return ResponseEntity.ok(service.updateEntry(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        service.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }
}

