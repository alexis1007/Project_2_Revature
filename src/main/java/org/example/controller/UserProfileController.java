package org.example.controller;

import java.util.List;

import org.example.Service.UserProfileService;
import org.example.model.User;
import org.example.model.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user-profiles")
public class UserProfileController {
    private static final Logger log = LoggerFactory.getLogger(UserProfileController.class);
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllUserProfiles(HttpServletRequest request) {
        // Only manager can gat a list of all user-profiles
        User sessionUser = (User) request.getAttribute("user");
        log.info("User [{}] wants a list of all users", sessionUser.getId());

        if(sessionUser.getUserType().getId() != 1) {
            log.warn("Access Denied for user [{}]",sessionUser.getId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        log.info("Users retrieve [{}]",userProfileService.findAllUserProfiles().size());
        return ResponseEntity.ok(userProfileService.findAllUserProfiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable Long id, HttpServletRequest request) {
        // Only manager and user itself can get user-profile information
        User sessionUser = (User) request.getAttribute("user");
        log.info("User [{}] wants user []", sessionUser.getId());
        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
            log.warn("Access Denied for user [{}]",sessionUser.getId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return userProfileService.findUserProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserProfile> registerUserProfile(@RequestBody UserProfile profile) {
        UserProfile savedProfile = userProfileService.registerUserProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable Long id,
                                                         @RequestBody UserProfile profileDetails,
                                                         HttpServletRequest request) {
        // Only manager and user itself can update user-profile
        User sessionUser = (User) request.getAttribute("user");
        log.info("User [{}] requests to update user [{}]",sessionUser.getId(),id);
        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
            log.warn("Access denied for user [{}]", sessionUser.getId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return userProfileService.updateUserProfile(id, profileDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id, HttpServletRequest request) {
        // Only manager and user itself can delete a user-profile
        User sessionUser = (User) request.getAttribute("user");
        log.info("User [{}] requests to delete user [{}]", sessionUser.getId(),id);
        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
            log.warn("Access denied to user [{}]", sessionUser.getId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        boolean deleted = userProfileService.deleteUserProfile(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
