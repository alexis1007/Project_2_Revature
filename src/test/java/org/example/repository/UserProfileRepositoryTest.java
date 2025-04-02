package org.example.repository;

import org.example.model.UserProfile;
import org.example.model.User;
import org.example.model.UserType;
import org.example.model.MailingAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private MailingAddressRepository mailingAddressRepository;

    @Test
    public void testSaveUserProfile() {
        UserType userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

        MailingAddress mailingAddress = new MailingAddress();
        mailingAddress.setStreet("123 Main St");
        mailingAddress.setCity("Anytown");
        mailingAddress.setState("CA");
        mailingAddress.setZip("12345");
        mailingAddress.setCountry("USA");
        mailingAddressRepository.save(mailingAddress);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setMailingAddress(mailingAddress);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("123-456-7890");
        userProfile.setCreditScore(700);
        userProfile.setBirthDate(LocalDate.of(1990, 1, 1));
        userProfileRepository.save(userProfile);

        Optional<UserProfile> foundUserProfile = userProfileRepository.findById(userProfile.getId());
        assertTrue(foundUserProfile.isPresent());
        assertEquals("John", foundUserProfile.get().getFirstName());
    }

    @Test
    public void testUpdateUserProfile() {
        UserType userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

        MailingAddress mailingAddress = new MailingAddress();
        mailingAddress.setStreet("123 Main St");
        mailingAddress.setCity("Anytown");
        mailingAddress.setState("CA");
        mailingAddress.setZip("12345");
        mailingAddress.setCountry("USA");
        mailingAddressRepository.save(mailingAddress);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setMailingAddress(mailingAddress);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("123-456-7890");
        userProfile.setCreditScore(700);
        userProfile.setBirthDate(LocalDate.of(1990, 1, 1));
        userProfileRepository.save(userProfile);

        userProfile.setFirstName("Jane");
        userProfileRepository.save(userProfile);

        Optional<UserProfile> foundUserProfile = userProfileRepository.findById(userProfile.getId());
        assertTrue(foundUserProfile.isPresent());
        assertEquals("Jane", foundUserProfile.get().getFirstName());
    }

    @Test
    public void testDeleteUserProfile() {
        UserType userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

        MailingAddress mailingAddress = new MailingAddress();
        mailingAddress.setStreet("123 Main St");
        mailingAddress.setCity("Anytown");
        mailingAddress.setState("CA");
        mailingAddress.setZip("12345");
        mailingAddress.setCountry("USA");
        mailingAddressRepository.save(mailingAddress);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setMailingAddress(mailingAddress);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("123-456-7890");
        userProfile.setCreditScore(700);
        userProfile.setBirthDate(LocalDate.of(1990, 1, 1));
        userProfileRepository.save(userProfile);

        userProfileRepository.delete(userProfile);

        Optional<UserProfile> foundUserProfile = userProfileRepository.findById(userProfile.getId());
        assertFalse(foundUserProfile.isPresent());
    }
}
