package com.example.SpringAddressBookAppDevelopment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SpringAddressBookAppDevelopment.model.AddressBookEntry;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressBookRepository extends JpaRepository<AddressBookEntry, Long> {
}
