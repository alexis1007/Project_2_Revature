package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "application_statuses", schema = "loans")
public class ApplicationStatus{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_statuses_id")
    private int id;

    @Column(name = "application_statuses", length = 10, unique = true)
    private String status;

    @Column(name = "description", length = 100)
    private String description;

    public ApplicationStatus() {}

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription(){
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getApplicationStatus() {
        return this.status;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ApplicationStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}