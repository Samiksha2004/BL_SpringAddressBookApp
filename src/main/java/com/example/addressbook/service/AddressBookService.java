package com.example.addressbook.service;

import com.example.addressbook.model.AddressBookEntry;
import com.example.addressbook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return repository.findById(id).orElse(null);
    }

    public AddressBookEntry updateEntry(Long id, AddressBookEntry updatedEntry) {
        Optional<AddressBookEntry> existingEntry = repository.findById(id);
        if (existingEntry.isPresent()) {
            AddressBookEntry entry = existingEntry.get();
            entry.setName(updatedEntry.getName());
            entry.setPhoneNumber(updatedEntry.getPhoneNumber());
            entry.setEmail(updatedEntry.getEmail());
            return repository.save(entry);
        }
        return null;
    }

    public void deleteEntry(Long id) {
        repository.deleteById(id);
    }
}
