package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBookEntry;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AddressBookService implements IAddressBookService {
    private final List<AddressBookEntry> contactList;
    private final AtomicInteger idCounter = new AtomicInteger(1);

    @Autowired
    public AddressBookService(List<AddressBookEntry> contactList) {
        this.contactList = contactList;
    }

    @Override
    @Cacheable(value = "contacts")
    public List<AddressBookEntry> getAllContacts() {
        return contactList;
    }


    @Override
    @Cacheable(value = "contact", key = "#id")
    public AddressBookEntry getContactById(int id) {
        return contactList.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    @CachePut(value = "contact", key = "#result.id")
    @CacheEvict(value = "contacts", allEntries = true)
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
    @CachePut(value = "contact", key = "#id")
    @CacheEvict(value = "contacts", allEntries = true)
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
    @Caching(evict = {
            @CacheEvict(value = "contact", key = "#id"),
            @CacheEvict(value = "contacts", allEntries = true)
    })
    public void deleteContact(int id) {
        contactList.removeIf(contact -> contact.getId() == id);
    }
}
