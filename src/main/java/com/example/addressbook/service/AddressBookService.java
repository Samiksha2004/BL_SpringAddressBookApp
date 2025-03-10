package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBookEntry;
import com.example.addressbook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository repository;

    public List<AddressBookDTO> getAllEntries() {
        List<AddressBookEntry> entries = repository.findAll();
        return entries.stream()
                .map(entry -> new AddressBookDTO(entry.getName(), entry.getEmail(), entry.getPhoneNumber()))
                .collect(Collectors.toList());
    }

    public AddressBookDTO createEntry(AddressBookDTO dto) {
        AddressBookEntry entry = new AddressBookEntry();
        entry.setName(dto.getName());
        entry.setEmail(dto.getEmail());
        entry.setPhoneNumber(dto.getPhoneNumber());

        AddressBookEntry savedEntry = repository.save(entry);
        return new AddressBookDTO(savedEntry.getName(), savedEntry.getEmail(), savedEntry.getPhoneNumber());
    }
}
