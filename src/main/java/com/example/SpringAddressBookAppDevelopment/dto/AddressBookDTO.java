package com.example.SpringAddressBookAppDevelopment.dto;

import lombok.Data;

@Data  // Generates Getters, Setters, toString, equals, hashCode
public class AddressBookDTO {
    private String name;
    private String address;
    private String phoneNumber;
}
