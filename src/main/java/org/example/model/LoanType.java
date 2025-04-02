package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loan_type", schema = "loans")
public class LoanType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_type_id")
    private Long id;

    @Column(name = "loan_type", length = 10, unique = true)
    private String loanType;

    @OneToMany(mappedBy = "loanType")
    @JsonIgnore
    private List<LoanApplication> loanApplications = new ArrayList<>();

    public List<LoanApplication> getLoanApplications() {
        return loanApplications;
    }

    public void setLoanApplications(List<LoanApplication> loanApplications) {
        this.loanApplications = loanApplications;
    }

    public LoanType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    @Override
    public String toString() {
        return "LoanType{" +
                "id=" + id +
                ", loanType='" + loanType + '\'' +
                '}';
    }
}
