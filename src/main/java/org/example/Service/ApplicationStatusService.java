package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.ApplicationStatus;
import org.example.repository.ApplicationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStatusService implements ApplicationStatusInterface{

    private final ApplicationStatusRepository  applicationStatusRepository;
    public List<ApplicationStatus> findAllApplicationStatus;

    @Autowired
    public ApplicationStatusService(ApplicationStatusRepository applicationStatusRepository){
        this.applicationStatusRepository = applicationStatusRepository;
    }

    @Override
    public List<ApplicationStatus> findAllApplicationStatus(){return applicationStatusRepository.findAll();}

    @Override
    public Optional<ApplicationStatus> findApplicationStatusById(Long id){
        return applicationStatusRepository.findById(id);
    }

    @Override
    public ApplicationStatus createApplicationStatus(ApplicationStatus applicationStatus) {
        return applicationStatusRepository.save(applicationStatus);
    }

    @Override
    public Optional<ApplicationStatus> updateApplicationStatus(Long id, ApplicationStatus applicationStatus) {
        return applicationStatusRepository.findById(id).map(existingApplicationStatus -> {
            // Update fields of existing application status
            existingApplicationStatus.setApplicationStatus(applicationStatus.getApplicationStatus());
            // Save the updated entity
            return applicationStatusRepository.save(existingApplicationStatus);
        });
    }

    @Override
    public boolean deleteApplicationStatus(Long id) {
        if(applicationStatusRepository.existsById(id)) {
            applicationStatusRepository.deleteById(id);
            return true;
        }
        return true;
    }
}
