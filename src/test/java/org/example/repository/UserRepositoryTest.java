package org.example.repository;

import org.example.model.User;
import org.example.model.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Test
    public void testFindByUsername() {
        UserType userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("testuser");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    public void testFindByUsernameAndPasswordHash() {
        UserType userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsernameAndPasswordHash("testuser", "password");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }
}
