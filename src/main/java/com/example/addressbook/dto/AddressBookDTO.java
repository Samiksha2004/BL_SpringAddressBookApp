package com.example.addressbook.dto;

import com.example.addressbook.model.AddressBookEntry;

public class AddressBookDTO {
    private String name;
    private String email;
    private String phone;
    private String address;  // ✅ Added Address Field

    public AddressBookDTO() {} // Default Constructor

    public AddressBookDTO(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;  // ✅ Fixed: address field initialized
    }

    // Constructor to map from Entity to DTO
    public AddressBookDTO(AddressBookEntry entry) {
        this.name = entry.getName();
        this.email = entry.getEmail();
        this.phone = entry.getPhone();
        this.address = entry.getAddress();
    }

    // ✅ Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
