package com.fiap.techmesa.infrastructure.gateway;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.gateway.ClientGateway;
import com.fiap.techmesa.application.usecase.exception.ClientNotFoundException;
import com.fiap.techmesa.infrastructure.persistence.entity.AddressEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.TableRestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClientGatewayImpl implements ClientGateway {

    private final ClientRepository clientRepository;

    @Override
    public Client save(final Client client) {
        ClientEntity clientEntity = mapToEntity(client);
        ClientEntity savedClientEntity = clientRepository.save(clientEntity);
        return mapToDomain(savedClientEntity);
    }

//    @Override
//    public Optional<Client> findByPartName(final String partName) {
//        return clientRepository.findByNameContainsIgnoreCase(partName)
//            .map(this::mapToDomain);
//    }

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
        final var clientFound =
            clientRepository
                .findById(client.getId())
                .orElseThrow(() -> new ClientNotFoundException(client.getId()));

        final var clientEntity =
            ClientEntity.builder()
                .id(clientFound.getId())
                .name(client.getName())
                .email(client.getEmail())
                .registrationDate(client.getRegistrationDate())
                .address(mapToEntity(client.getAddress()))
                .build();
        
        final var updated = clientRepository.save(clientEntity);
        
        return this.toResponse(updated);
    }

    @Transactional
    @Override
    public void delete(final int id) {
        clientRepository.deleteById(id);
    }

    private ClientEntity mapToEntity(final Client client) {
        return ClientEntity.builder()
            .id(client.getId())
            .name(client.getName())
            .email(client.getEmail())
            .registrationDate(client.getRegistrationDate())
            .address(mapToEntity(client.getAddress()))
            .build();
    }

    private Client mapToDomain(final ClientEntity entity) {
        return Client.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .registrationDate(entity.getRegistrationDate())
            .address(mapToDomain(entity.getAddress()))
            .build();
    }

    private AddressEntity mapToEntity(final Address address) {
        return AddressEntity.builder()
            .id(address.getId())
            .street(address.getStreet())
            .number(address.getNumber())
            .neighborhood(address.getNeighborhood())
            .city(address.getCity())
            .state(address.getState())
            .country(address.getCountry())
            .cep(address.getCep())
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
            .cep(entity.getCep())
            .build();
    }

    private Client toResponse(final ClientEntity entity) {
        return new Client(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getRegistrationDate(),
            mapToDomain(entity.getAddress()),
            entity.getReserve().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList()));
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

    private TableRestaurant mapToDomain(final TableRestaurantEntity entity) {
        return TableRestaurant.builder()
            .id(entity.getId())
            .tableIdentification(entity.getTableIdentification())
            .restaurantId(entity.getRestaurant().getId())
            .reserveId(entity.getReserve().getId())
            .numberSeats(entity.getNumberSeats())
            .statusTableOccupation(entity.getStatusTableOccupation())
            .tablePosition(entity.getTablePosition())
            .build();
    }

	@Override
	public Optional<Client> findByPartName(String partName) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}
