package com.fiap.techmesa.infrastructure.gateway;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.gateway.ClientGateway;
import com.fiap.techmesa.infrastructure.persistence.entity.AddressEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.TableRestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.AddressRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClientGatewayImpl implements ClientGateway {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    @Override
    public Client save(final Client client) {
        ClientEntity clientEntity = mapToEntity(client);
        ClientEntity savedClientEntity = clientRepository.save(clientEntity);
        return mapToDomain(savedClientEntity);
    }

    @Override
    public Optional<Client> findByPartName(final String partName) {
        return clientRepository.findByNameContainsIgnoreCase(partName)
                .flatMap(list -> list.stream().findFirst())
                .map(this::mapToDomain);
    }

    @Override
    public Optional<Client> findByName(final String name) {
        return clientRepository.findByName(name).map(this::mapToDomain);
    }

    @Override
    public Optional<Client> findById(final int id) {
        return clientRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Optional<Client> findByEmail(final String email) {
        return clientRepository.findByEmail(email).map(this::mapToDomain);
    }

    @Override
    public Client update(final Client client) {
        final var clientFound = clientRepository.findById(client.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Client with id [%s] not found", client.getId())));

        final var address = addressRepository.findById(client.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Address with id [%s] not found", client.getAddressId())));
        
        final var clientEntity = ClientEntity.builder()
                .id(clientFound.getId())
                .name(client.getName())
                .email(client.getEmail())
                .address(address)
                .registrationDate(client.getRegistrationDate())
                .reserves(client.getReserves().stream()
                        .map(this::mapToEntity)
                        .collect(Collectors.toList()))
                .build();

        final var updated = clientRepository.save(clientEntity);

        return mapToDomain(updated);
    }

    @Transactional
    @Override
    public void delete(final int id) {
        clientRepository.deleteById(id);
    }

    private ClientEntity mapToEntity(final Client client) {
        return ClientEntity.builder()
                .name(client.getName())
                .email(client.getEmail())
                .address(AddressEntity.builder().id(client.getAddressId()).build())
                .registrationDate(client.getRegistrationDate())
                .reserves(client.getReserves().stream()
                        .map(this::mapToEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    private Client mapToDomain(final ClientEntity entity) {
        return Client.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .addressId(entity.getAddress().getId())
                .registrationDate(entity.getRegistrationDate())
                .reserves(entity.getReserves().stream()
                        .map(this::mapToDomain)
                        .collect(Collectors.toList()))
                .build();
    }

    private ReserveEntity mapToEntity(final Reserve reserve) {
        return ReserveEntity.builder()
                .id(reserve.getId())
                .client(ClientEntity.builder().id(reserve.getClientId()).build())
                .restaurant(RestaurantEntity.builder().id(reserve.getRestaurantId()).build())
                .numberPeople(reserve.getNumberPeople())
                .dateReserve(reserve.getDateReserve())
                .dateCreated(reserve.getDateCreated())
                .startReserve(reserve.getStartReserve())
                .toleranceMinutes(reserve.getToleranceMinutes())
                .timeLimit(reserve.getTimeLimit())
                .statusReserve(reserve.getStatusReserve())
                .tableRestaurant(reserve.getTableRestaurants().stream()
                        .map(this::mapToEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    private Reserve mapToDomain(final ReserveEntity entity) {
        return Reserve.builder()
                .id(entity.getId())
                .clientId(entity.getClient().getId())
                .restaurantId(entity.getRestaurant().getId())
                .numberPeople(entity.getNumberPeople())
                .dateReserve(entity.getDateReserve())
                .dateCreated(entity.getDateCreated())
                .startReserve(entity.getStartReserve())
                .toleranceMinutes(entity.getToleranceMinutes())
                .timeLimit(entity.getTimeLimit())
                .statusReserve(entity.getStatusReserve())
                .tableRestaurants(entity.getTableRestaurant().stream()
                        .map(this::mapToDomain)
                        .collect(Collectors.toList()))
                .build();
    }

    private TableRestaurantEntity mapToEntity(final TableRestaurant tableRestaurant) {
        return TableRestaurantEntity.builder()
                .id(tableRestaurant.getId())
                .tableIdentification(tableRestaurant.getTableIdentification())
                .restaurant(RestaurantEntity.builder().id(tableRestaurant.getRestaurantId()).build())
                .reserve(tableRestaurant.getReserveId() != null ? ReserveEntity.builder().id(tableRestaurant.getReserveId()).build() : null)
                .numberSeats(tableRestaurant.getNumberSeats())
                .statusTableOccupation(tableRestaurant.getStatusTableOccupation())
                .tablePosition(tableRestaurant.getTablePosition())
                .build();
    }

    private TableRestaurant mapToDomain(final TableRestaurantEntity entity) {
        return TableRestaurant.builder()
                .id(entity.getId())
                .tableIdentification(entity.getTableIdentification())
                .restaurantId(entity.getRestaurant().getId())
                .reserveId(entity.getReserve() != null ? entity.getReserve().getId() : null)
                .numberSeats(entity.getNumberSeats())
                .statusTableOccupation(entity.getStatusTableOccupation())
                .tablePosition(entity.getTablePosition())
                .build();
    }
}
