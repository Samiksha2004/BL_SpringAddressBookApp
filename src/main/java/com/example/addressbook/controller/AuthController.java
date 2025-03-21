package com.example.addressbook.controller;

import com.example.addressbook.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.addressbook.dto.UserDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    // UC1 - Register API
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");
        UserDTO userDTO = userService.registerUser(username, email, password);

        Map<String, Object> message = Map.of(
                "username", username,
                "email", email,
                "event", "USER_REGISTERED"
        );
        rabbitTemplate.convertAndSend("addressbook-exchange", "addressbook-routingKey", message);

        return ResponseEntity.ok(userDTO);
    }

    // UC1 - Login API
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody Map<String, String> request) {
        log.info("Login API hit for user: {}", request.get("username"));
        String username = request.get("username");
        String password = request.get("password");
        UserDTO userDTO = userService.loginUser(username, password);


        return ResponseEntity.ok(userDTO);
    }

    // UC2 - Forgot Password API
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String resetToken = userService.generateResetToken(username);

        Map<String, String> response = Map.of(
                "message", "Reset token generated successfully",
                "resetToken", resetToken
        );

        return ResponseEntity.ok(response);
    }

    // UC2 - Reset Password API
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        userService.resetPassword(token, newPassword);

        Map<String, String> response = Map.of(
                "message", "Password has been reset successfully"
        );

        return ResponseEntity.ok(response);
    }
}
