package com.example.SpringAddressBookAppDevelopment.service;

import com.example.SpringAddressBookAppDevelopment.repository.AddressBookRepository;
import com.example.SpringAddressBookAppDevelopment.model.AddressBookEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressBookService {
    @Autowired
    private AddressBookRepository repository;

    public List<AddressBookEntry> getAllEntries() {
        return repository.findAll();
    }

    public Optional<AddressBookEntry> getEntryById(Long id) {
        return repository.findById(id);
    }

    public AddressBookEntry addEntry(AddressBookEntry entry) {
        return repository.save(entry);
    }

    public AddressBookEntry updateEntry(Long id, AddressBookEntry entryDetails) {
        return repository.findById(id).map(entry -> {
            entry.setName(entryDetails.getName());
            entry.setPhone(entryDetails.getPhone());
            entry.setEmail(entryDetails.getEmail());
            return repository.save(entry);
        }).orElseThrow(() -> new RuntimeException("Entry not found"));
    }

    public void deleteEntry(Long id) {
        repository.deleteById(id);
    }
}
