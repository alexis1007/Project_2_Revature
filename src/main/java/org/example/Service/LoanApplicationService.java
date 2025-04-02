package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.LoanApplication;
import org.example.repository.LoanAppRepository;
import org.springframework.stereotype.Service;

@Service
public class LoanApplicationService implements LoanAppInterface {

    private final LoanAppRepository loanAppRepository;

    public LoanApplicationService(LoanAppRepository loanAppRepository) {
        this.loanAppRepository = loanAppRepository;
    }

    @Override
    public List<LoanApplication> findAllLoans() {
        return loanAppRepository.findAll();
    }

    @Override
    public Optional<LoanApplication> findLoanById(Long id) {
        return loanAppRepository.findById(id);
    }

    @Override
    public LoanApplication createLoan(LoanApplication loanApplication) {
        return loanAppRepository.save(loanApplication);
    }

    @Override
    public Optional<LoanApplication> updateLoan(Long id, LoanApplication loanApplication) {
        return loanAppRepository.findById(id).map(existingLoan -> {
            existingLoan.setLoanType(loanApplication.getLoanType());
            existingLoan.setApplicationStatus(loanApplication.getApplicationStatus());
            existingLoan.setUserProfile(loanApplication.getUserProfile());
            existingLoan.setPrincipalBalance(loanApplication.getPrincipalBalance());
            existingLoan.setInterest(loanApplication.getInterest());
            existingLoan.setTermLength(loanApplication.getTermLength());
            existingLoan.setTotalBalance(loanApplication.getTotalBalance());
            return loanAppRepository.save(existingLoan);
        });
    }

    @Override
    public boolean deleteLoan(Long id) {
        return loanAppRepository.findById(id).map(task -> {
            loanAppRepository.delete(task);
            return true;
        }).orElse(false);
    }

    @Override
    public Optional<LoanApplication> approveLoan(Long id, LoanApplication loanApplication) {
        return loanAppRepository.findById(id).map(existingLoan -> {
            existingLoan.setApplicationStatus(loanApplication.getApplicationStatus());
            return loanAppRepository.save(existingLoan);
        });
    }

    @Override
    public Optional<LoanApplication> rejectLoan(Long id, LoanApplication loanApplication) {
        return loanAppRepository.findById(id).map(existingLoan -> {
            existingLoan.setApplicationStatus(loanApplication.getApplicationStatus());
            return loanAppRepository.save(existingLoan);
        });
    }

}
