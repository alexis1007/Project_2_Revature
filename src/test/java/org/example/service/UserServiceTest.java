package org.example.service;

import java.util.Optional;

import org.example.Service.UserService;
import org.example.model.User;
import org.example.model.UserType;
import org.example.repository.UserRepository;
import org.example.repository.UserTypeRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserType userType;

    @BeforeEach
    public void setUp() {
        userType = new UserType();
        userType.setId(1L);
        userType.setUserType("USER");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
    }

    @Test
    public void testRegisterUser() {
        when(userTypeRepository.findById(any(Long.class))).thenReturn(Optional.of(userType));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals(user.getUsername(), registeredUser.getUsername());
        assertTrue(BCrypt.checkpw("password", registeredUser.getPasswordHash()));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindUserById() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
        verify(userRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userTypeRepository.findById(any(Long.class))).thenReturn(Optional.of(userType));

        User updatedUserDetails = new User();
        updatedUserDetails.setUsername("updateduser");
        updatedUserDetails.setPasswordHash(BCrypt.hashpw("newpassword", BCrypt.gensalt(4)));
        updatedUserDetails.setUserType(userType);

        Optional<User> updatedUser = userService.updateUser(1L, updatedUserDetails);

        assertTrue(updatedUser.isPresent());
        assertEquals("updateduser", updatedUser.get().getUsername());
        assertTrue(BCrypt.checkpw("newpassword", updatedUser.get().getPasswordHash()));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUser_UserTypeNotFound() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userTypeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        User updatedUserDetails = new User();
        updatedUserDetails.setUsername("updateduser");
        updatedUserDetails.setPasswordHash(BCrypt.hashpw("newpassword", BCrypt.gensalt(4)));
        updatedUserDetails.setUserType(userType);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(1L, updatedUserDetails);
        });

        assertEquals("User type not found with id 1", exception.getMessage());
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));

        boolean isDeleted = userService.deleteUser(1L);

        assertTrue(isDeleted);
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    public void testValidateUser() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        user.setPasswordHash(BCrypt.hashpw("password", BCrypt.gensalt(4)));

        Optional<User> validatedUser = userService.validateUser("testuser", "password");

        assertTrue(validatedUser.isPresent());
        assertEquals(user.getUsername(), validatedUser.get().getUsername());
        verify(userRepository, times(1)).findByUsername(any(String.class));
    }
}
