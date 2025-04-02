package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.ApplicationStatus;

public interface ApplicationStatusInterface {

    List<ApplicationStatus> findAllApplicationStatus();

    Optional<ApplicationStatus> findApplicationStatusById(Long id);

    ApplicationStatus createApplicationStatus(ApplicationStatus applicationStatus);

    Optional<ApplicationStatus> updateApplicationStatus(Long id, ApplicationStatus applicationStatus);

    boolean deleteApplicationStatus(Long id);
}
