package org.example.controller;

import java.util.List;

import org.example.Service.UserService;
import org.example.model.User;
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
@RequestMapping("/api/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    // private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService=userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(HttpServletRequest request){
        // Only manager can gat a list of all users
        User sessionUser = (User) request.getAttribute("user");

        //log user
        log.info("User [{}] wants list of all users", sessionUser.getId());
        if(sessionUser.getUserType().getId() != 1) {
            log.warn("Access Denied for user [{}]",sessionUser.getId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        log.info("Users retrieve [{}]",userService.findAllUsers().size());
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id, HttpServletRequest request) {
        // Only manager and user itself can get user information
        User sessionUser = (User) request.getAttribute("user");

        log.info("User [{}] requests information about user [{}]", sessionUser.getId(),id);

        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
            log.warn("Access denied for user [{}] to get user [{}]",sessionUser.getId(),id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        log.info("Request to register user with name [{}]",user.getUsername());
        User savedUser = userService.registerUser(user);
        log.info("User [{}] succesfull register", savedUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody User userDetails,
                                           HttpServletRequest request){
        // Only manager and user itself can update user
        User sessionUser = (User) request.getAttribute("user");
        log.info("User [{}] requests to update user [{}]",sessionUser.getId(),id);
        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
            log.warn("Access denied for user [{}]", sessionUser.getId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return userService.updateUser(id, userDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        // Only manager and user itself can delete user
        User sessionUser = (User) request.getAttribute("user");

        log.info("User [{}] requests to delete user [{}]",sessionUser.getId(),id);

        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
            log.warn("Access denied for user [{}] to delete user [{}]", sessionUser.getId(),id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            log.info("User [{}] deleted ", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("User [{}] not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
