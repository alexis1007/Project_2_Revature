package org.example.controller;

import java.util.List;

import org.example.Service.LoanApplicationService;
import org.example.Service.UserProfileService;
import org.example.model.LoanApplication;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/loans")
public class LoanAppController {
    private static final Logger log = LoggerFactory.getLogger(LoanAppController.class);
    private final LoanApplicationService loanAppService;

    private final UserProfileService userProfileService;

    public LoanAppController(LoanApplicationService loanAppService, UserProfileService userProfileService) {
        this.loanAppService = loanAppService;
        this.userProfileService = userProfileService;

    }

    @GetMapping
    public ResponseEntity<List<LoanApplication>> getAllLoans() {
        log.info("Request all loans");
        return ResponseEntity.ok(loanAppService.findAllLoans());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<LoanApplication>> getAllLoans(@PathVariable Long id) {
        log.info("Request all loans by user");
        return ResponseEntity.ok(userProfileService.findUserProfileById(id).get().getLoanApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplication> getLoanById(@PathVariable Long id) {
        log.info("Request loan with id [{}]",id);
        return loanAppService.findLoanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new loan for the currently logged-in user.
     *
     * @param loan    The loan to be created.
     * @param request The HttpServletRequest containing the logged-in user.
     * @return The saved loan.
     */
    @PostMapping
    public ResponseEntity<LoanApplication> createLoan(@RequestBody LoanApplication loan, HttpServletRequest request) {
        User sessionUser = (User) request.getAttribute("user");
        if (sessionUser == null) {
            log.warn("Access denied - no authenticated user");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        log.info("User [{}] requests to create a new loan", sessionUser.getId());
        loan.setUserProfile(sessionUser.getUserProfile());
        LoanApplication savedLoan = loanAppService.createLoan(loan);

        log.info("Loan created by user [{}]",sessionUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLoan);

    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanApplication> updateLoan(@PathVariable Long id,
            @RequestBody LoanApplication loanApplication) {
        log.info("Updating loan with id [{}]",id);
        return loanAppService.updateLoan(id, loanApplication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        boolean deleted = loanAppService.deleteLoan(id);
        log.info("Deleting loan with id [{}]",id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LoanApplication> approveLoan(@PathVariable Long id,
            @RequestBody LoanApplication loanApplication) {
        log.info("Approving loan with id [{}]",id);
        return loanAppService.approveLoan(id, loanApplication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LoanApplication> rejectLoan(@PathVariable Long id,
            @RequestBody LoanApplication loanApplication) {
        log.info("Rejecting loan with id [{}]",id);
        return loanAppService.rejectLoan(id, loanApplication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
