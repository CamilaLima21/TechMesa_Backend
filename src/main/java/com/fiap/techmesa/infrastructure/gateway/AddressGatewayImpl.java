package com.fiap.techmesa.infrastructure.gateway;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.gateway.AddressGateway;
import com.fiap.techmesa.infrastructure.persistence.entity.AddressEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressGatewayImpl implements AddressGateway {

    private final AddressRepository addressRepository;

    @Override
    public Address save(final Address address) {
        AddressEntity addressEntity = mapToEntity(address);
        AddressEntity savedAddressEntity = addressRepository.save(addressEntity);
        return mapToDomain(savedAddressEntity);
    }

    @Override
    public Optional<Address> findById(final int id) {
        return addressRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Address update(final Address address) {
        final var addressFound =
            addressRepository.findById(address.getId())
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        final var addressEntity =
            AddressEntity.builder()
                .id(addressFound.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .zipCode(address.getZipCode())
                .build();

        final var updated = addressRepository.save(addressEntity);

        return mapToDomain(updated);
    }

    @Transactional
    @Override
    public void delete(final int id) {
        addressRepository.deleteById(id);
    }

    private AddressEntity mapToEntity(final Address address) {
    	
        return AddressEntity.builder()
            .street(address.getStreet())
            .number(address.getNumber())
            .neighborhood(address.getNeighborhood())
            .city(address.getCity())
            .state(address.getState())
            .country(address.getCountry())
            .zipCode(address.getZipCode())
            .build();
    }

    private Address mapToDomain(final AddressEntity entity) {
        return Address.builder()
            .id(entity.getId())
            .street(entity.getStreet())
            .number(entity.getNumber())
            .neighborhood(entity.getNeighborhood())
            .city(entity.getCity())
            .state(entity.getState())
            .country(entity.getCountry())
            .zipCode(entity.getZipCode())
            .build();
    }
}
