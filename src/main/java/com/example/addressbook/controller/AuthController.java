package com.example.addressbook.controller;

import com.example.addressbook.dto.UserDTO;
import com.example.addressbook.model.User;
import com.example.addressbook.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // User Registration API
    @PostMapping("/register")
    public UserDTO register(@RequestBody UserRequest request) {
        return userService.registerUser(request.getUsername(), request.getPassword());
    }

    // User Login API
    @PostMapping("/login")
    public UserDTO login(@RequestBody UserRequest request) {
        return userService.loginUser(request.getUsername(), request.getPassword());
    }

    //Get User Profile API (Requires JWT Token)
    @GetMapping("/profile")
    public UserDTO getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.getUserByUsername(username);
        return new UserDTO(user.getUsername(), "Profile fetched successfully!");
    }

    // Inner static class for request body
    static class UserRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
