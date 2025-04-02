package org.example.repository;

import org.example.model.MailingAddress;
import org.example.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailingAddressRepository extends JpaRepository<MailingAddress, Long> {


}
