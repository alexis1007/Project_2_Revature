package org.example.controller;

import org.example.Service.UserTypeService;
import org.example.model.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user_types")
public class UserTypeController {

    private final UserTypeService userTypeService;

    public UserTypeController(UserTypeService userTypeService){
        this.userTypeService = userTypeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserType> getUserTypeById(@PathVariable Long id) {
        return userTypeService.findUserTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<UserType> createUserType(@RequestBody UserType userType) {
         UserType savedUserType = userTypeService.createUserType(userType);
         return ResponseEntity.status(HttpStatus.CREATED).body(savedUserType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserType> updateUserType
            (@PathVariable Long id, @RequestBody UserType userTypeDetails){
            return userTypeService.updateUserType(id, userTypeDetails)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserType(@PathVariable Long id) {
        boolean deleted = userTypeService.deleteUserType(id);
        return deleted ? ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}
