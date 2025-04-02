package org.example.controller;


import java.util.List;
import java.util.Optional;

import org.example.Service.ApplicationStatusService;
import org.example.model.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/application_statuses")
@CrossOrigin(origins = "*")
public class ApplicationStatusController {

    private final ApplicationStatusService applicationStatusService;

    @Autowired
    public ApplicationStatusController(ApplicationStatusService applicationStatusService){
        this.applicationStatusService = applicationStatusService;
    }

    @GetMapping
    public List<ApplicationStatus> getAllApplicationStatus(){
        return applicationStatusService.findAllApplicationStatus();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationStatus> getApplicationStatusById(@PathVariable Long id){
        Optional<ApplicationStatus> applicationStatus = applicationStatusService.findApplicationStatusById(id);
        return applicationStatus.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ApplicationStatus> createApplicationStatus(@RequestBody ApplicationStatus applicationStatus){
        ApplicationStatus createdApplicationStatus = applicationStatusService.createApplicationStatus(applicationStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApplicationStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationStatus> updateApplicationStatus(@PathVariable Long id, @RequestBody ApplicationStatus applicationStatus){
        Optional<ApplicationStatus> updatedApplicationStatus = applicationStatusService.updateApplicationStatus(id, applicationStatus);
        return updatedApplicationStatus.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicationStatus(@PathVariable Long id){
        boolean isDeleted = applicationStatusService.deleteApplicationStatus(id);
        return isDeleted ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
