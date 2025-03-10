package com.example.addressbook.service;

import com.example.addressbook.model.AddressBookEntry;
import com.example.addressbook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository repository;

    public AddressBookEntry addEntry(AddressBookEntry entry) {
        return repository.save(entry);
    }

    public List<AddressBookEntry> getAllEntries() {
        return repository.findAll();
    }

    public AddressBookEntry getEntryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry Not Found"));
    }

    public void deleteEntry(Long id) {
        repository.deleteById(id);
    }
}
