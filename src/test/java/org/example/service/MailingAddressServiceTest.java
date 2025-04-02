package org.example.service;

import org.example.Service.MailingAddressService;
import org.example.model.MailingAddress;
import org.example.repository.MailingAddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MailingAddressServiceTest {

    @Mock
    private MailingAddressRepository mailingAddressRepository;

    @InjectMocks
    private MailingAddressService mailingAddressService;

    private MailingAddress mailingAddress;

    @BeforeEach
    public void setUp() {
        mailingAddress = new MailingAddress();
        mailingAddress.setId(1L);
        mailingAddress.setStreet("123 Main St");
        mailingAddress.setCity("Anytown");
        mailingAddress.setState("Anystate");
        mailingAddress.setZip("12345");
        mailingAddress.setCountry("USA");
    }

    @Test
    public void testCreateAddress() {
        when(mailingAddressRepository.save(any(MailingAddress.class))).thenReturn(mailingAddress);

        MailingAddress createdAddress = mailingAddressService.createAddress(mailingAddress);

        assertNotNull(createdAddress);
        assertEquals(mailingAddress.getStreet(), createdAddress.getStreet());
        verify(mailingAddressRepository, times(1)).save(any(MailingAddress.class));
    }

    @Test
    public void testFindAddressById() {
        when(mailingAddressRepository.findById(any(Long.class))).thenReturn(Optional.of(mailingAddress));

        Optional<MailingAddress> foundAddress = mailingAddressService.findAddressById(1L);

        assertTrue(foundAddress.isPresent());
        assertEquals(mailingAddress.getStreet(), foundAddress.get().getStreet());
        verify(mailingAddressRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void testUpdateAddress() {
        when(mailingAddressRepository.findById(any(Long.class))).thenReturn(Optional.of(mailingAddress));
        when(mailingAddressRepository.save(any(MailingAddress.class))).thenReturn(mailingAddress);

        MailingAddress updatedAddressDetails = new MailingAddress();
        updatedAddressDetails.setStreet("456 Elm St");
        updatedAddressDetails.setCity("Othertown");
        updatedAddressDetails.setState("Otherstate");
        updatedAddressDetails.setZip("67890");
        updatedAddressDetails.setCountry("Canada");

        Optional<MailingAddress> updatedAddress = mailingAddressService.updateAddress(1L, updatedAddressDetails);

        assertTrue(updatedAddress.isPresent());
        assertEquals("456 Elm St", updatedAddress.get().getStreet());
        verify(mailingAddressRepository, times(1)).save(any(MailingAddress.class));
    }

    @Test
    public void testDeleteAddress() {
        when(mailingAddressRepository.findById(any(Long.class))).thenReturn(Optional.of(mailingAddress));
        doNothing().when(mailingAddressRepository).delete(any(MailingAddress.class));

        boolean isDeleted = mailingAddressService.deleteAddress(1L);

        assertTrue(isDeleted);
        verify(mailingAddressRepository, times(1)).delete(any(MailingAddress.class));
    }
}
