package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.UserProfile;
import org.example.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService implements UserProfileServiceInterface {
    private static final Logger log = LoggerFactory.getLogger(UserProfileService.class);
    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public List<UserProfile> findAllUserProfiles() {
        log.info("Fetching all user profiles...");
        return userProfileRepository.findAll();
    }

    @Override
    public Optional<UserProfile> findUserProfileById(Long id) {
        log.info("Searching for user profile with ID: {}", id);
        return userProfileRepository.findById(id);
    }

    @Override
    public UserProfile registerUserProfile(UserProfile user) {
        log.info("Registering new user profile: {}", user);
        return userProfileRepository.save(user);
    }

    @Override
    public Optional<UserProfile> updateUserProfile(Long id, UserProfile profile) {
        log.info("Attempting to update user profile with ID: {}", id);
        return userProfileRepository.findById(id).map(existingProfile -> {
            existingProfile.setFirstName(profile.getFirstName());
            existingProfile.setLastName(profile.getLastName());
            existingProfile.setPhoneNumber(profile.getPhoneNumber());
            return userProfileRepository.save(existingProfile);
        });
    }

    @Override
    public boolean deleteUserProfile(Long id) {
        log.info("Attempting to delete user profile with ID: {}", id);
        return userProfileRepository.findById(id).map(profile -> {
            userProfileRepository.delete(profile);
            return true;
        }).orElse(false);
    }
}
