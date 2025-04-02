package org.example.service;

import org.example.Service.LoanApplicationService;
import org.example.model.LoanApplication;
import org.example.model.ApplicationStatus;
import org.example.model.LoanType;
import org.example.model.UserProfile;
import org.example.repository.LoanAppRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanApplicationServiceTest {

    @Mock
    private LoanAppRepository loanAppRepository;

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    private LoanApplication loanApplication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loanApplication = new LoanApplication();
        loanApplication.setId(1L);
        loanApplication.setPrincipalBalance(new BigDecimal("1000.00"));
        loanApplication.setInterest(new BigDecimal("5.00"));
        loanApplication.setTermLength(12);
        loanApplication.setTotalBalance(new BigDecimal("1050.00"));
        loanApplication.setApplicationStatus(new ApplicationStatus());
        loanApplication.setLoanType(new LoanType());
        loanApplication.setUserProfile(new UserProfile());
    }

    @Test
    public void testFindAllLoans() {
        loanApplicationService.findAllLoans();
        verify(loanAppRepository, times(1)).findAll();
    }

    @Test
    public void testFindLoanById() {
        when(loanAppRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        Optional<LoanApplication> foundLoan = loanApplicationService.findLoanById(1L);
        assertTrue(foundLoan.isPresent());
        assertEquals(loanApplication.getId(), foundLoan.get().getId());
    }

    @Test
    public void testCreateLoan() {
        when(loanAppRepository.save(loanApplication)).thenReturn(loanApplication);
        LoanApplication createdLoan = loanApplicationService.createLoan(loanApplication);
        assertNotNull(createdLoan);
        assertEquals(loanApplication.getId(), createdLoan.getId());
    }

    @Test
    public void testUpdateLoan() {
        when(loanAppRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        when(loanAppRepository.save(loanApplication)).thenReturn(loanApplication);
        Optional<LoanApplication> updatedLoan = loanApplicationService.updateLoan(1L, loanApplication);
        assertTrue(updatedLoan.isPresent());
        assertEquals(loanApplication.getId(), updatedLoan.get().getId());
    }

    @Test
    public void testDeleteLoan() {
        when(loanAppRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        boolean isDeleted = loanApplicationService.deleteLoan(1L);
        assertTrue(isDeleted);
        verify(loanAppRepository, times(1)).delete(loanApplication);
    }

    @Test
    public void testApproveLoan() {
        when(loanAppRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        when(loanAppRepository.save(loanApplication)).thenReturn(loanApplication);
        Optional<LoanApplication> approvedLoan = loanApplicationService.approveLoan(1L, loanApplication);
        assertTrue(approvedLoan.isPresent());
        assertEquals(loanApplication.getId(), approvedLoan.get().getId());
    }

    @Test
    public void testRejectLoan() {
        when(loanAppRepository.findById(1L)).thenReturn(Optional.of(loanApplication));
        when(loanAppRepository.save(loanApplication)).thenReturn(loanApplication);
        Optional<LoanApplication> rejectedLoan = loanApplicationService.rejectLoan(1L, loanApplication);
        assertTrue(rejectedLoan.isPresent());
        assertEquals(loanApplication.getId(), rejectedLoan.get().getId());
    }
}
