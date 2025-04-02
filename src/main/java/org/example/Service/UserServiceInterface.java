package org.example.Service;

import org.example.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    List<User> findAllUsers();
    Optional<User> findUserById(Long id);
    User registerUser(User user);
    Optional<User> updateUser(Long id, User userDetails);
    boolean deleteUser(Long id);

    Optional<User> validateUser (String username, String passwordHash);
}
