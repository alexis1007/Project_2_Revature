package org.example.DTO;

import org.example.model.LoanApplication;

public class LoanResponseDto {
    private LoanApplication loanApplication;

    public LoanResponseDto(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }

    public LoanApplication getLoan() {
        return loanApplication;
    }

    public void setLoan(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }
}
