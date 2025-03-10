package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBookEntry;

import java.util.List;

public interface IAddressBookService {
    List<AddressBookEntry> getAllContacts();
    AddressBookEntry getContactById(int id);
    AddressBookEntry addContact(AddressBookDTO addressBookDTO);
    AddressBookEntry updateContact(int id, AddressBookDTO addressBookDTO);
    void deleteContact(int id);
}
