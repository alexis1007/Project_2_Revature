package org.example.service;

import org.example.Service.UserProfileService;
import org.example.model.UserProfile;
import org.example.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    private UserProfile userProfile;

    @BeforeEach
    public void setUp() {
        userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("1234567890");
        userProfile.setCreditScore(700);
    }

    @Test
    public void testRegisterUserProfile() {
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfile registeredUserProfile = userProfileService.registerUserProfile(userProfile);

        assertNotNull(registeredUserProfile);
        assertEquals(userProfile.getFirstName(), registeredUserProfile.getFirstName());
        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    public void testFindUserProfileById() {
        when(userProfileRepository.findById(any(Long.class))).thenReturn(Optional.of(userProfile));

        Optional<UserProfile> foundUserProfile = userProfileService.findUserProfileById(1L);

        assertTrue(foundUserProfile.isPresent());
        assertEquals(userProfile.getFirstName(), foundUserProfile.get().getFirstName());
        verify(userProfileRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void testUpdateUserProfile() {
        when(userProfileRepository.findById(any(Long.class))).thenReturn(Optional.of(userProfile));
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfile updatedUserProfileDetails = new UserProfile();
        updatedUserProfileDetails.setFirstName("Jane");
        updatedUserProfileDetails.setLastName("Smith");

        Optional<UserProfile> updatedUserProfile = userProfileService.updateUserProfile(1L, updatedUserProfileDetails);

        assertTrue(updatedUserProfile.isPresent());
        assertEquals("Jane", updatedUserProfile.get().getFirstName());
        verify(userProfileRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    public void testDeleteUserProfile() {
        when(userProfileRepository.findById(any(Long.class))).thenReturn(Optional.of(userProfile));
        doNothing().when(userProfileRepository).delete(any(UserProfile.class));

        boolean isDeleted = userProfileService.deleteUserProfile(1L);

        assertTrue(isDeleted);
        verify(userProfileRepository, times(1)).delete(any(UserProfile.class));
    }
}
