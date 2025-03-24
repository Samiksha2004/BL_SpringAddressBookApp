package com.example.addressbook.controller;

import com.example.addressbook.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.addressbook.dto.UserDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Auth API", description = "Authentication APIs including Register, Login, Forgot & Reset Password")
public class AuthController {

    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    @Operation(summary = "Register a new user", description = "Register API for creating a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully")
    })
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

    @Operation(summary = "Login user", description = "Login API to authenticate a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody Map<String, String> request) {
        log.info("Login API hit for user: {}", request.get("username"));
        String username = request.get("username");
        String password = request.get("password");
        UserDTO userDTO = userService.loginUser(username, password);

        return ResponseEntity.ok(userDTO);
    }

    @Operation(summary = "Forgot password", description = "Generate reset token for forgot password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reset token generated successfully")
    })
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

    @Operation(summary = "Reset password", description = "Reset password using token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password has been reset successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token")
    })
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
