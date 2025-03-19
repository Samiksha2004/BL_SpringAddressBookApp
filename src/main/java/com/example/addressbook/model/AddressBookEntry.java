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
    private String address;  // Added Address Field

    //Default Constructor (Required for JPA)
    public AddressBookEntry() {}

    //Constructor for creating a new entry (WITH ID)
    public AddressBookEntry(Long id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Constructor for DTO to Entity Conversion (without ID)
    public AddressBookEntry(com.example.addressbook.dto.AddressBookDTO dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.address = dto.getAddress();
    }

    // Setter Methods (Fixes 'Cannot resolve method' errors)
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //Getter Methods
    public Long getId() {
        return id;
    }

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
