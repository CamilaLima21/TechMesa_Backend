package com.fiap.techmesa.infrastructure.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.infrastructure.persistence.entity.AddressEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.AddressRepository;

public class AddressGatewayImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressGatewayImpl addressGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {

        Address address = new Address();
        address.setId(1);
        address.setStreet("Test Street");
        address.setNumber(1);
        address.setNeighborhood("bairro");
        address.setCity("city");
        address.setState("state");
        address.setCountry("Brasil");
        address.setZipCode("0123456");
     
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("Test Street");
        addressEntity.setNumber(1);
        addressEntity.setNeighborhood("bairro");
        addressEntity.setCity("city");
        addressEntity.setState("state");
        addressEntity.setCountry("Brasil");
        addressEntity.setZipCode("0123456");

        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

        Address result = addressGateway.save(address);

        assertNotNull(result);
        assertEquals("Test Street", result.getStreet());
    }

    @Test
    void testFindById() {

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("Test Street");

        when(addressRepository.findById(1)).thenReturn(Optional.of(addressEntity));

        Optional<Address> result = addressGateway.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Test Street", result.get().getStreet());
    }

    @Test
    void testUpdate() {

        Address address = new Address();
        address.setId(1);
        address.setStreet("Updated Street");
        address.setNumber(456);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("Updated Street");
        addressEntity.setNumber(456);

        when(addressRepository.findById(1)).thenReturn(Optional.of(addressEntity));
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

        Address result = addressGateway.update(address);

        assertNotNull(result);
        assertEquals("Updated Street", result.getStreet());
    }

    @Test
    void testDelete() {
        addressGateway.delete(1);
        verify(addressRepository, times(1)).deleteById(1);
    }
}