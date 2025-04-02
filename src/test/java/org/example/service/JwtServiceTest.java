package org.example.service;

import java.util.Date;

import org.example.Service.JwtService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken("testuser", "USER");
        assertNotNull(token);
    }

    @Test
    public void testExtractUsername() {
        String token = jwtService.generateToken("testuser", "USER");
        String username = jwtService.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testExtractExpiration() {
        String token = jwtService.generateToken("testuser", "USER");
        Date expiration = jwtService.extractExpiration(token);
        assertNotNull(expiration);
    }

    @Test
    public void testValidateToken() {
        String token = jwtService.generateToken("testuser", "USER");
        boolean isValid = jwtService.validateToken(token, "testuser");
        assertTrue(isValid);
    }

    @Test
    public void testValidateTokenWithInvalidUsername() {
        String token = jwtService.generateToken("testuser", "USER");
        boolean isValid = jwtService.validateToken(token, "invaliduser");
        assertFalse(isValid);
    }
}
