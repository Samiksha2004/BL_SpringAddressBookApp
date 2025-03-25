package com.example.addressbook.controller;


import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBookEntry;
import com.example.addressbook.service.IAddressBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class AddressBookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IAddressBookService addressBookService;

    @InjectMocks
    private AddressBookController addressBookController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressBookController).build();
    }

    @Test
    void testGetAllContacts() throws Exception {
        List<AddressBookEntry> contacts = Arrays.asList(
                new AddressBookEntry(1L, "John Doe", "john@example.com", "1234567890", "Address1"),
                new AddressBookEntry(2L, "Jane Doe", "jane@example.com", "0987654321", "Address2")
        );

        when(addressBookService.getAllContacts()).thenReturn(contacts);

        mockMvc.perform(get("/api/addressbook"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    void testGetContactById() throws Exception {
        AddressBookEntry contact = new AddressBookEntry(1L, "John Doe", "john@example.com", "1234567890", "Address1");

        when(addressBookService.getContactById(1)).thenReturn(contact);

        mockMvc.perform(get("/api/addressbook/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.phone").value("1234567890"))
                .andExpect(jsonPath("$.address").value("Address1"));
    }

    @Test
    void testAddContact() throws Exception {
        AddressBookDTO addressBookDTO = new AddressBookDTO("John Doe", "john@example.com", "1234567890", "Address1");
        AddressBookEntry contact = new AddressBookEntry(1L, "John Doe", "john@example.com", "1234567890", "Address1");

        when(addressBookService.addContact(any(AddressBookDTO.class))).thenReturn(contact);

        mockMvc.perform(post("/api/addressbook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"phone\":\"1234567890\", \"address\":\"Address1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.address").value("Address1"));
    }

    @Test
    void testUpdateContact() throws Exception {
        AddressBookDTO addressBookDTO = new AddressBookDTO("John Doe", "john@example.com", "1234567890", "Updated Address");
        AddressBookEntry updatedContact = new AddressBookEntry(1L, "John Doe", "john@example.com", "1234567890", "Updated Address");

        when(addressBookService.updateContact(eq(1), any(AddressBookDTO.class))).thenReturn(updatedContact);

        mockMvc.perform(put("/api/addressbook/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"phone\":\"1234567890\", \"address\":\"Updated Address\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.address").value("Updated Address"));
    }


    @Test
    void testDeleteContact() throws Exception {
        doNothing().when(addressBookService).deleteContact(1);

        mockMvc.perform(delete("/api/addressbook/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Contact deleted successfully!"));
    }
}