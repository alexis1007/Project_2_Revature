package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.UserType;
import org.example.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> findAllUserTypes() {
        return userTypeRepository.findAll();
    }

    public Optional<UserType> findUserTypeById(Long id) {
        return userTypeRepository.findById(id);
    }

    public UserType createUserType(UserType userType) {
        return userTypeRepository.save(userType);
    }

    public Optional<UserType> updateUserType(Long id, UserType userType) {
        return userTypeRepository.findById(id).map(existingType -> {
            existingType.setUserType(userType.getUserType());
            return userTypeRepository.save(existingType);
        });
    }

    public boolean deleteUserType(Long id) {
        return userTypeRepository.findById(id).map(userType -> {
            userTypeRepository.delete(userType);
            return true;
        }).orElse(false);
    }
}
