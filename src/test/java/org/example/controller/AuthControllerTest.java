package org.example.controller;

import java.util.Optional;

import org.example.DTO.AuthRequest;
import org.example.Main;
import org.example.Service.JwtService;
import org.example.Service.UserProfileService;
import org.example.Service.UserService;
import org.example.model.User;
import org.example.model.UserProfile;
import org.example.model.UserType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = Main.class)
public class AuthControllerTest {
    
    @Autowired
    private AuthController authController;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private JwtService jwtService;
    
    @MockBean
    private UserProfileService userProfileService;
    
    @Test
    public void testRegisterUser() {
        // Setup
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setUser(user);
        
        when(userService.registerUser(any(User.class))).thenReturn(user);
        when(userProfileService.registerUserProfile(any(UserProfile.class))).thenReturn(userProfile);
        
        // Execute
        ResponseEntity<UserProfile> response = authController.registerUser(userProfile);
        
        // Verify
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }
    
    @Test
    public void testLogin() {
        // Setup
        UserType userType = new UserType();
        userType.setId(1L);
        userType.setUserType("CUSTOMER");
        
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setUserType(userType);
        
        AuthRequest request = new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("password");
        
        when(userService.validateUser("testuser", "password")).thenReturn(Optional.of(user));
        when(jwtService.generateToken("testuser", "CUSTOMER")).thenReturn("token");
        
        // Execute
        ResponseEntity<?> response = authController.login(request);
        
        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
