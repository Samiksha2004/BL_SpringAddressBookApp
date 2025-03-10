package com.example.addressbook.controller;

import com.example.addressbook.dto.AddressBookDTO;
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

    @GetMapping
    public ResponseEntity<List<AddressBookDTO>> getAllEntries() {
        return ResponseEntity.ok(service.getAllEntries());
    }

    @PostMapping
    public ResponseEntity<AddressBookDTO> createEntry(@RequestBody AddressBookDTO dto) {
        return ResponseEntity.ok(service.createEntry(dto));
    }
}
