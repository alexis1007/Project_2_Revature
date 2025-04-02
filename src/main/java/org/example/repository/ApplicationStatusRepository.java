package org.example.repository;

import org.example.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long>{
}
