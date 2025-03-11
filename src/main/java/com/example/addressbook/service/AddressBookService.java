package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBookEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AddressBookService implements IAddressBookService {
    private final List<AddressBookEntry> contactList = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    @Override
    public List<AddressBookEntry> getAllContacts() {
        return contactList;
    }

    @Override
    public AddressBookEntry getContactById(int id) {
        return contactList.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public AddressBookEntry addContact(AddressBookDTO addressBookDTO) {
        AddressBookEntry newContact = new AddressBookEntry(
                (long)idCounter.getAndIncrement(),
                addressBookDTO.getName(),
                addressBookDTO.getEmail(),
                addressBookDTO.getPhone(),
                addressBookDTO.getAddress()
        );
        contactList.add(newContact);
        return newContact;
    }

    @Override
    public AddressBookEntry updateContact(int id, AddressBookDTO addressBookDTO) {
        Optional<AddressBookEntry> contactOpt = contactList.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst();

        if (contactOpt.isPresent()) {
            AddressBookEntry contact = contactOpt.get();
            contact.setName(addressBookDTO.getName());
            contact.setEmail(addressBookDTO.getEmail());
            contact.setPhone(addressBookDTO.getPhone());
            contact.setAddress(addressBookDTO.getAddress());
            return contact;
        }
        return null;
    }

    @Override
    public void deleteContact(int id) {
        contactList.removeIf(contact -> contact.getId() == id);
    }
}
