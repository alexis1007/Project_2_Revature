package org.example.repository;

import org.example.model.LoanApplication;
import org.example.model.ApplicationStatus;
import org.example.model.LoanType;
import org.example.model.UserProfile;
import org.example.model.User;
import org.example.model.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LoanAppRepositoryTest {

    @Autowired
    private LoanAppRepository loanAppRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private ApplicationStatusRepository applicationStatusRepository;

    @Autowired
    private LoanTypeRepository loanTypeRepository;

    @Test
    public void testSaveLoanApplication() {
        UserType userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("123-456-7890");
        userProfile.setCreditScore(700);
        userProfile.setBirthDate(LocalDate.of(1990, 1, 1));
        userProfileRepository.save(userProfile);

        ApplicationStatus applicationStatus = new ApplicationStatus();
        applicationStatus.setStatus("PENDING");
        applicationStatusRepository.save(applicationStatus);

        LoanType loanType = new LoanType();
        loanType.setLoanType("PERSONAL");
        loanTypeRepository.save(loanType);

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setUserProfile(userProfile);
        loanApplication.setApplicationStatus(applicationStatus);
        loanApplication.setLoanType(loanType);
        loanApplication.setPrincipalBalance(new BigDecimal("10000.00"));
        loanApplication.setInterest(new BigDecimal("5.00"));
        loanApplication.setTermLength(12);
        loanApplication.setTotalBalance(new BigDecimal("10500.00"));
        loanAppRepository.save(loanApplication);

        Optional<LoanApplication> foundLoanApplication = loanAppRepository.findById(loanApplication.getId());
        assertTrue(foundLoanApplication.isPresent());
        assertEquals(new BigDecimal("10000.00"), foundLoanApplication.get().getPrincipalBalance());
    }

    @Test
    public void testUpdateLoanApplication() {
        UserType userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("123-456-7890");
        userProfile.setCreditScore(700);
        userProfile.setBirthDate(LocalDate.of(1990, 1, 1));
        userProfileRepository.save(userProfile);

        ApplicationStatus applicationStatus = new ApplicationStatus();
        applicationStatus.setStatus("PENDING");
        applicationStatusRepository.save(applicationStatus);

        LoanType loanType = new LoanType();
        loanType.setLoanType("PERSONAL");
        loanTypeRepository.save(loanType);

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setUserProfile(userProfile);
        loanApplication.setApplicationStatus(applicationStatus);
        loanApplication.setLoanType(loanType);
        loanApplication.setPrincipalBalance(new BigDecimal("10000.00"));
        loanApplication.setInterest(new BigDecimal("5.00"));
        loanApplication.setTermLength(12);
        loanApplication.setTotalBalance(new BigDecimal("10500.00"));
        loanAppRepository.save(loanApplication);

        loanApplication.setPrincipalBalance(new BigDecimal("15000.00"));
        loanAppRepository.save(loanApplication);

        Optional<LoanApplication> foundLoanApplication = loanAppRepository.findById(loanApplication.getId());
        assertTrue(foundLoanApplication.isPresent());
        assertEquals(new BigDecimal("15000.00"), foundLoanApplication.get().getPrincipalBalance());
    }

    @Test
    public void testDeleteLoanApplication() {
        UserType userType = new UserType();
        userType.setUserType("USER");
        userTypeRepository.save(userType);

        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
        userRepository.save(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("123-456-7890");
        userProfile.setCreditScore(700);
        userProfile.setBirthDate(LocalDate.of(1990, 1, 1));
        userProfileRepository.save(userProfile);

        ApplicationStatus applicationStatus = new ApplicationStatus();
        applicationStatus.setStatus("PENDING");
        applicationStatusRepository.save(applicationStatus);

        LoanType loanType = new LoanType();
        loanType.setLoanType("PERSONAL");
        loanTypeRepository.save(loanType);

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setUserProfile(userProfile);
        loanApplication.setApplicationStatus(applicationStatus);
        loanApplication.setLoanType(loanType);
        loanApplication.setPrincipalBalance(new BigDecimal("10000.00"));
        loanApplication.setInterest(new BigDecimal("5.00"));
        loanApplication.setTermLength(12);
        loanApplication.setTotalBalance(new BigDecimal("10500.00"));
        loanAppRepository.save(loanApplication);

        loanAppRepository.delete(loanApplication);

        Optional<LoanApplication> foundLoanApplication = loanAppRepository.findById(loanApplication.getId());
        assertFalse(foundLoanApplication.isPresent());
    }
}
