package org.example.controller;

import java.util.Optional;

import org.example.DTO.AuthRequest;
import org.example.DTO.AuthResponse;
import org.example.Service.JwtService;
import org.example.Service.UserProfileService;
import org.example.Service.UserService;
import org.example.model.User;
import org.example.model.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final JwtService jwtService;
    private final UserProfileService userProfileService;

    public AuthController(UserService userService, UserProfileService userProfileService, JwtService jwtService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserProfile> registerUser(@RequestBody UserProfile userProfile) {
        log.info("Register new user");
        User savedUser = userService.registerUser(userProfile.getUser());
        userProfile.setUser(savedUser);
        UserProfile savedUserProfile = userProfileService.registerUserProfile(userProfile);
        log.info("Success register");
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserProfile);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Optional<User> userOpt = userService.validateUser(authRequest.getUsername(), authRequest.getPassword());
        log.info("Login");
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            log.info("Loggin success");
            String userType = (user.getUserType() != null) ? user.getUserType().getUserType() : "DEFAULT";
            String token = jwtService.generateToken(user.getUsername(), userType);
            return ResponseEntity.ok(new AuthResponse(token, user));
        } else {
            log.warn("Logging failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
