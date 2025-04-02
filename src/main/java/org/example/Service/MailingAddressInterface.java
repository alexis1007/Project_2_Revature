package org.example.Service;

import java.util.List;
import java.util.Optional;

import org.example.model.MailingAddress;
public interface MailingAddressInterface {

    List<MailingAddress> findAllAddresses();

    Optional<MailingAddress> findAddressById(Long id);

    MailingAddress createAddress(MailingAddress mailingAddress);

    Optional<MailingAddress> updateAddress(Long id, MailingAddress mailingAddress);

    boolean deleteAddress(Long id);
}
