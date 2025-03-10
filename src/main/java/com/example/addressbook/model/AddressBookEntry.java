package com.example.addressbook.model;

import jakarta.persistence.*;

@Entity
@Table(name = "address_book")
public class AddressBookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    // Default Constructor (Required for JPA)
    public AddressBookEntry() {}

    // Constructor for creating a new entity
    public AddressBookEntry(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Constructor that accepts AddressBookDTO
    public AddressBookEntry(com.example.addressbook.dto.AddressBookDTO dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
    }

    // ✅ Add an `update()` method to modify existing entries
    public void update(com.example.addressbook.dto.AddressBookDTO dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
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
}
