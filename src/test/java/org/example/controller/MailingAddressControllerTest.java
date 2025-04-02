package org.example.controller;

import org.example.Service.MailingAddressService;
import org.example.model.MailingAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MailingAddressControllerTest {

    @Mock
    private MailingAddressService mailingAddressService;

    @InjectMocks
    private MailingAddressController mailingAddressController;

    private MockMvc mockMvc;

    private MailingAddress mailingAddress;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mailingAddressController).build();

        mailingAddress = new MailingAddress();
        mailingAddress.setId(1L);
        mailingAddress.setStreet("123 Main St");
        mailingAddress.setCity("Anytown");
        mailingAddress.setState("Anystate");
        mailingAddress.setZip("12345");
        mailingAddress.setCountry("USA");
    }

    @Test
    public void testGetMailingAddressById() throws Exception {
        when(mailingAddressService.findAddressById(any(Long.class))).thenReturn(Optional.of(mailingAddress));

        mockMvc.perform(get("/api/mailing/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("123 Main St"))
                .andExpect(jsonPath("$.city").value("Anytown"))
                .andExpect(jsonPath("$.state").value("Anystate"))
                .andExpect(jsonPath("$.zip").value("12345"))
                .andExpect(jsonPath("$.country").value("USA"));

        verify(mailingAddressService, times(1)).findAddressById(any(Long.class));
    }

    @Test
    public void testCreateMailingAddress() throws Exception {
        when(mailingAddressService.createAddress(any(MailingAddress.class))).thenReturn(mailingAddress);

        mockMvc.perform(post("/api/mailing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"street\":\"123 Main St\",\"city\":\"Anytown\",\"state\":\"Anystate\",\"zip\":\"12345\",\"country\":\"USA\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.street").value("123 Main St"))
                .andExpect(jsonPath("$.city").value("Anytown"))
                .andExpect(jsonPath("$.state").value("Anystate"))
                .andExpect(jsonPath("$.zip").value("12345"))
                .andExpect(jsonPath("$.country").value("USA"));

        verify(mailingAddressService, times(1)).createAddress(any(MailingAddress.class));
    }

    @Test
    public void testUpdateMailingAddress() throws Exception {
        when(mailingAddressService.updateAddress(any(Long.class), any(MailingAddress.class))).thenReturn(Optional.of(mailingAddress));

        mockMvc.perform(put("/api/mailing/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"street\":\"123 Main St\",\"city\":\"Anytown\",\"state\":\"Anystate\",\"zip\":\"12345\",\"country\":\"USA\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("123 Main St"))
                .andExpect(jsonPath("$.city").value("Anytown"))
                .andExpect(jsonPath("$.state").value("Anystate"))
                .andExpect(jsonPath("$.zip").value("12345"))
                .andExpect(jsonPath("$.country").value("USA"));

        verify(mailingAddressService, times(1)).updateAddress(any(Long.class), any(MailingAddress.class));
    }

    @Test
    public void testDeleteMailingAddress() throws Exception {
        when(mailingAddressService.deleteAddress(any(Long.class))).thenReturn(true);

        mockMvc.perform(delete("/api/mailing/1"))
                .andExpect(status().isNoContent());

        verify(mailingAddressService, times(1)).deleteAddress(any(Long.class));
    }
}
