package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.LoanApplication;
import org.example.model.LoanType;
import org.example.repository.LoanAppRepository;
import org.example.repository.LoanTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoanTypeService implements LoanTypeInterface{

    private final LoanTypeRepository loanTypeRepository;

    private final LoanAppRepository loanAppRepository;
    @Autowired
    public LoanTypeService(LoanTypeRepository loanTypeRepository, LoanAppRepository loanAppRepository) {
        this.loanTypeRepository = loanTypeRepository;
        this.loanAppRepository = loanAppRepository;
    }


    @Override
    public List<LoanType> findAllLoanTypes() {
        return loanTypeRepository.findAll();
    }

    @Override
    public Optional<LoanType> findLoanTypeById(Long id) {
        return loanTypeRepository.findById(id);
    }

    @Override
    public LoanType createLoanType(LoanType loanType) {
        return loanTypeRepository.save(loanType);
    }

    @Override
    public Optional<LoanType> updateLoanType(Long id, LoanType loanType) {
        return loanTypeRepository.findById(id).map(existingLoanType -> {
            existingLoanType.setLoanType(loanType.getLoanType());
            return loanTypeRepository.save(existingLoanType);
        });
    }

    @Override
    public boolean deleteLoanType(Long id) {
        Optional<LoanType> optionalLoanType = loanTypeRepository.findById(id);

        if (optionalLoanType.isPresent()) {
            LoanType loanType = optionalLoanType.get();

            for (LoanApplication app : loanType.getLoanApplications()) {
                app.setLoanType(null);
                loanAppRepository.save(app);
            }

            loanTypeRepository.delete(loanType);
            return true;
        }

        return false;
    }
}
