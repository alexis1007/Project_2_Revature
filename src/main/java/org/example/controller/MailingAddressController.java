package org.example.controller;

import java.util.List;
import java.util.Optional;

import org.example.Service.MailingAddressService;
import org.example.model.MailingAddress;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/mailing")
public class MailingAddressController {

    private final MailingAddressService mailingAddressService;

    @Autowired
    public MailingAddressController(MailingAddressService mailingAddressService) {
        this.mailingAddressService = mailingAddressService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMailingAddresses(HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");

        if(sessionUser.getUserType().getId() != 1 ) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        List<MailingAddress> addresses = mailingAddressService.findAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MailingAddress> getMailingAddressById(@PathVariable Long id, HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");

        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<MailingAddress> address = mailingAddressService.findAddressById(id);

        return address.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<MailingAddress> createMailingAddress(@RequestBody MailingAddress mailingAddress) {

        MailingAddress createdAddress = mailingAddressService.createAddress(mailingAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MailingAddress> updateMailingAddress(@PathVariable Long id, @RequestBody MailingAddress mailingAddress, HttpServletRequest request) {
        // Only manager and user itself can update user
        User sessionUser = (User) request.getAttribute("user");
        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<MailingAddress> updatedAddress = mailingAddressService.updateAddress(id, mailingAddress);
        return updatedAddress.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMailingAddress(@PathVariable Long id, HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");

        if(sessionUser.getUserType().getId() != 1 && sessionUser.getId() != id) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        boolean isDeleted = mailingAddressService.deleteAddress(id);
        return isDeleted ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}