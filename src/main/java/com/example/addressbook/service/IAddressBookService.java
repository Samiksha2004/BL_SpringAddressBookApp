package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import java.util.*;

public interface IAddressBookService {
    List<AddressBookDTO> getAllContacts();
    AddressBookDTO getContactById(Long id);
    AddressBookDTO addContact(AddressBookDTO addressBookDTO);
    AddressBookDTO updateContact(Long id, AddressBookDTO addressBookDTO);
    void deleteContact(Long id);
}
