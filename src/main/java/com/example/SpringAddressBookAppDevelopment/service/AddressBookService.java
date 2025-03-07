package com.example.SpringAddressBookAppDevelopment.service;

import com.example.SpringAddressBookAppDevelopment.dto.AddressBookDTO;
import com.example.SpringAddressBookAppDevelopment.model.AddressBookEntry;
import com.example.SpringAddressBookAppDevelopment.repository.AddressBookRepository;
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

    public AddressBookEntry addEntry(AddressBookDTO dto) {
        AddressBookEntry entry = new AddressBookEntry(null, dto.getName(), dto.getPhoneNumber(), dto.getEmail()); // ✅ Fixed
        return repository.save(entry);
    }

    public AddressBookEntry updateEntry(Long id, AddressBookDTO dto) {
        AddressBookEntry entry = repository.findById(id).orElseThrow(() -> new RuntimeException("Entry not found"));
        entry.setName(dto.getName());
        entry.setPhone(dto.getPhoneNumber()); //
        entry.setEmail(dto.getEmail());
        return repository.save(entry);
    }

    public void deleteEntry(Long id) {
        repository.deleteById(id);
    }
}
