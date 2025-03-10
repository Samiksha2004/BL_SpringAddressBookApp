package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBookEntry;
import com.example.addressbook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressBookService implements IAddressBookService {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Override
    public List<AddressBookDTO> getAllContacts() {
        return addressBookRepository.findAll()
                .stream()
                .map(AddressBookDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AddressBookDTO getContactById(Long id) {
        AddressBookEntry contact = addressBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        return new AddressBookDTO(contact);
    }

    @Override
    public AddressBookDTO addContact(AddressBookDTO addressBookDTO) {
        AddressBookEntry contact = new AddressBookEntry(addressBookDTO);
        addressBookRepository.save(contact);
        return new AddressBookDTO(contact);
    }

    @Override
    public AddressBookDTO updateContact(Long id, AddressBookDTO addressBookDTO) {
        AddressBookEntry contact = addressBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        contact.update(addressBookDTO);
        addressBookRepository.save(contact);
        return new AddressBookDTO(contact);
    }

    @Override
    public void deleteContact(Long id) {
        addressBookRepository.deleteById(id);
    }
}
