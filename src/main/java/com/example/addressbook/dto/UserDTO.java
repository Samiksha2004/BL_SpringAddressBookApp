package com.example.addressbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String token;

    // Extra constructor for Register use-case (without token)
    public UserDTO(String id, String username) {
        this.id = id;
        this.username = username;
    }

    // Extra constructor for Login use-case (id + token)
    public UserDTO(String id, String token, boolean isLogin) {
        this.id = id;
        this.token = token;
    }
}
