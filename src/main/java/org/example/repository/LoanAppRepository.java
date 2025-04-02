package org.example.repository;

import org.example.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanAppRepository extends JpaRepository<LoanApplication, Long> {

}
