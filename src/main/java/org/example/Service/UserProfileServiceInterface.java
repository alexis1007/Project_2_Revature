package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.UserProfile;

public interface UserProfileServiceInterface {
    List<UserProfile> findAllUserProfiles();

    Optional<UserProfile> findUserProfileById(Long id);

    UserProfile registerUserProfile(UserProfile profile);

    Optional<UserProfile> updateUserProfile(Long id, UserProfile profile);

    boolean deleteUserProfile(Long id);
}
