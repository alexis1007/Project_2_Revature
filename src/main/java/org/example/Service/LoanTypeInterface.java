package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.LoanType;

public interface LoanTypeInterface {

        List<LoanType> findAllLoanTypes();
        
        Optional<LoanType> findLoanTypeById(Long id);
        
        LoanType createLoanType(LoanType loanType);
        
        Optional<LoanType> updateLoanType(Long id, LoanType loanType);
        
        boolean deleteLoanType(Long id);
}
