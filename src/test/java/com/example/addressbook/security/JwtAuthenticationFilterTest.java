package com.example.addressbook.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext(); // Clear security context before each test
    }

    @Test
    void testDoFilterInternal_WithAuthEndpoint_ShouldBypassFilter() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/auth/login");

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_WithValidToken_ShouldAuthenticateUser() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/secure-endpoint");
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");

        String username = "testuser";
        UserDetails userDetails = new User(username, "password", Collections.emptyList());

        when(jwtUtil.extractUsername("valid-token")).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken("valid-token", userDetails)).thenReturn(true);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain, times(1)).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(username, ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    @Test
    void testDoFilterInternal_WithInvalidToken_ShouldReturnUnauthorized() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/secure-endpoint");
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");

        doThrow(new RuntimeException("Invalid Token")).when(jwtUtil).extractUsername("invalid-token");

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_WithExpiredToken_ShouldReturnUnauthorized() throws ServletException, IOException {
        // Arrange
        when(request.getServletPath()).thenReturn("/api/secure-endpoint");
        when(request.getHeader("Authorization")).thenReturn("Bearer expired-token");

        String username = "testuser";
        UserDetails userDetails = new User(username, "password", Collections.emptyList());

        when(jwtUtil.extractUsername("expired-token")).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken("expired-token", userDetails)).thenReturn(false);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Validation Failed");
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}