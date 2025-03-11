package com.example.addressbook.service;

import com.example.addressbook.repository.UserRepository;
import com.example.addressbook.dto.UserDTO;
import com.example.addressbook.model.User;
import com.example.addressbook.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // (Single Constructor)
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    //Encode password method
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // User Registration
    public UserDTO registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Encode password
        userRepository.save(user);

        String token = jwtUtil.generateToken(username);
        return new UserDTO(username, token);
    }

    // User Login
    public UserDTO loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        String token = jwtUtil.generateToken(username);
        return new UserDTO(username, token);
    }
    //Get User By Username (For Profile)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
