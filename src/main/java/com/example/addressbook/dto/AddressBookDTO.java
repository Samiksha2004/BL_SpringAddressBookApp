package com.example.addressbook.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressBookDTO {
    private String name;
    private String email;
    private String phone;
    private String address;

    // Default Constructor
    public AddressBookDTO() {}

    // Parameterized Constructor
    public AddressBookDTO(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
