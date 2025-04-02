package org.example.repository;

import java.util.Optional;

import org.example.model.MailingAddress;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MailingAddressRepositoryTest {

    @Autowired
    private MailingAddressRepository mailingAddressRepository;

    @Test
    public void testSaveMailingAddress() {
        MailingAddress mailingAddress = new MailingAddress();
        mailingAddress.setStreet("123 Main St");
        mailingAddress.setCity("Anytown");
        mailingAddress.setState("CA");
        mailingAddress.setZip("12345");
        mailingAddress.setCountry("USA");
        mailingAddressRepository.save(mailingAddress);

        Optional<MailingAddress> foundMailingAddress = mailingAddressRepository.findById(mailingAddress.getId());
        assertTrue(foundMailingAddress.isPresent());
        assertEquals("123 Main St", foundMailingAddress.get().getStreet());
    }

    @Test
    public void testUpdateMailingAddress() {
        MailingAddress mailingAddress = new MailingAddress();
        mailingAddress.setStreet("123 Main St");
        mailingAddress.setCity("Anytown");
        mailingAddress.setState("CA");
        mailingAddress.setZip("12345");
        mailingAddress.setCountry("USA");
        mailingAddressRepository.save(mailingAddress);

        mailingAddress.setStreet("456 Elm St");
        mailingAddressRepository.save(mailingAddress);

        Optional<MailingAddress> foundMailingAddress = mailingAddressRepository.findById(mailingAddress.getId());
        assertTrue(foundMailingAddress.isPresent());
        assertEquals("456 Elm St", foundMailingAddress.get().getStreet());
    }

    @Test
    public void testDeleteMailingAddress() {
        MailingAddress mailingAddress = new MailingAddress();
        mailingAddress.setStreet("123 Main St");
        mailingAddress.setCity("Anytown");
        mailingAddress.setState("CA");
        mailingAddress.setZip("12345");
        mailingAddress.setCountry("USA");
        mailingAddressRepository.save(mailingAddress);

        mailingAddressRepository.delete(mailingAddress);

        Optional<MailingAddress> foundMailingAddress = mailingAddressRepository.findById(mailingAddress.getId());
        assertFalse(foundMailingAddress.isPresent());
    }
}
