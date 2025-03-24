package com.example.addressbook.controller;


import com.example.addressbook.dto.UserDTO;
import com.example.addressbook.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    // UC1 - Register API
    @Test
    void testRegister() throws Exception {
        // Mocking a UserDTO with id and username
        UserDTO userDTO = new UserDTO("1", "john_doe");

        when(userService.registerUser("john_doe", "john@example.com", "password123")).thenReturn(userDTO);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john_doe\",\"email\":\"john@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))   // Expecting id
                .andExpect(jsonPath("$.username").value("john_doe")) // Username matches DTO
                .andExpect(jsonPath("$.token").doesNotExist()); // Token shouldn't exist for register

        verify(rabbitTemplate, times(1)).convertAndSend(eq("addressbook-exchange"), eq("addressbook-routingKey"), anyMap());
    }


    // UC1 - Login API
    @Test
    void testLogin() throws Exception {
        // Mocking a UserDTO with id and token (for login)
        UserDTO userDTO = new UserDTO("1", "sample-token", true);

        when(userService.loginUser("john_doe", "password123")).thenReturn(userDTO);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john_doe\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))  // Expecting id
                .andExpect(jsonPath("$.token").value("sample-token")) // Token should be present
                .andExpect(jsonPath("$.username").doesNotExist()); // Username is not included in this DTO
    }


    // UC2 - Forgot Password API
    @Test
    void testForgotPassword() throws Exception {
        when(userService.generateResetToken("john_doe")).thenReturn("reset-token-123");

        mockMvc.perform(post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john_doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Reset token generated successfully"))
                .andExpect(jsonPath("$.resetToken").value("reset-token-123"));
    }

    // UC2 - Reset Password API
    @Test
    void testResetPassword() throws Exception {
        doNothing().when(userService).resetPassword("reset-token-123", "newPassword123");

        mockMvc.perform(post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"reset-token-123\",\"newPassword\":\"newPassword123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password has been reset successfully"));
    }
}