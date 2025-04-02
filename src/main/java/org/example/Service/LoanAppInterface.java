package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.LoanApplication;

/**
 *
 * @author Propietario
 */
public interface LoanAppInterface {
    List<LoanApplication> findAllLoans();

    Optional<LoanApplication> findLoanById(Long id);

    LoanApplication createLoan(LoanApplication loanApplication);

    Optional<LoanApplication> updateLoan(Long id, LoanApplication loanApplication);

    boolean deleteLoan(Long id);

    Optional<LoanApplication> approveLoan(Long id, LoanApplication loanApplication);

    Optional<LoanApplication> rejectLoan(Long id, LoanApplication loanApplicationd);

}
