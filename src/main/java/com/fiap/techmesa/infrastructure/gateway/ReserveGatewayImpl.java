package com.fiap.techmesa.infrastructure.gateway;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.application.gateway.ReserveGateway;
import com.fiap.techmesa.application.usecase.exception.ReserveNotFoundException;
import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.TableRestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.ClientRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.ReserveRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReserveGatewayImpl implements ReserveGateway {

    private final ReserveRepository reserveRepository;
    private final ClientRepository clientRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Reserve save(final Reserve reserve) {
        final Optional<ClientEntity> clientEntityFound =
            clientRepository.findById(reserve.getClientId());
        
        if (clientEntityFound.isEmpty()) {
            throw new IllegalArgumentException(
                String.format("Client with id [%s] not found", reserve.getRestaurantId()));
        }
        
        final Optional<RestaurantEntity> restaurantEntityFound =
            restaurantRepository.findById(reserve.getRestaurantId());
        
        if (restaurantEntityFound.isEmpty()) {
            throw new IllegalArgumentException(
                String.format("Restaurant with id [%s] not found", reserve.getRestaurantId()));
        }
        
        final var clientEntity = clientEntityFound.get();
        final var restaurantEntity = restaurantEntityFound.get();
        
        final var reserveEntity =
            ReserveEntity.builder()
                .client(clientEntity)
                .restaurant(restaurantEntity)
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
        
        final var saved = reserveRepository.save(reserveEntity);
        
        return mapToDomain(saved);
    }

    @Override
    public Pagination<Reserve> findAll(Page page) {
        var pageRequest = PageRequest.of(page.page(), page.size());
        var reservePage = reserveRepository.findAll(pageRequest);
        var reserves = reservePage.getContent().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());

        return new Pagination<>(reservePage.getNumber(), reservePage.getSize(), reservePage.getTotalPages(),
                reservePage.getTotalElements(), reservePage.getNumberOfElements(), reserves);
    }

    @Override
    public Optional<Reserve> findById(final int id) {
        return reserveRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Optional<List<ReserveEntity>> findByRestaurantIdAndDate(final Integer restaurantId, final LocalDate dateReserve) {
        final Optional<RestaurantEntity> restaurantEntityFound =
            restaurantRepository.findById(restaurantId);

        if (restaurantEntityFound.isEmpty()) {
            return Optional.empty();
        }

        final var restaurantEntity = restaurantEntityFound.get();

        return reserveRepository.findByRestaurantAndDateReserve(restaurantEntity, dateReserve);
    }

    @Override
    public Optional<List<ReserveEntity>> findByRestaurantIdAndClientIdAndData(final Integer restaurantId, final Integer clientId, final LocalDate dateReserve) {
        final Optional<RestaurantEntity> restaurantEntityFound =
            restaurantRepository.findById(restaurantId);

        final Optional<ClientEntity> clientEntityFound =
            clientRepository.findById(clientId);

        if (restaurantEntityFound.isEmpty() || clientEntityFound.isEmpty()) {
            return Optional.empty();
        }

        final var restaurantEntity = restaurantEntityFound.get();
        final var clientEntity = clientEntityFound.get();

        return reserveRepository.findByRestaurantAndClientAndDateReserve(restaurantEntity, clientEntity, dateReserve);
    }

    @Transactional
    @Override
    public void delete(final int id) {
        reserveRepository.deleteById(id);
    }

    @Override
    public Reserve update(final Reserve reserve) {
        final var reserveFound =
            reserveRepository.findById(reserve.getId())
                .orElseThrow(() -> new ReserveNotFoundException(reserve.getId()));

        final var reserveEntity =
            ReserveEntity.builder()
                .id(reserveFound.getId())
                .client(reserveFound.getClient())
                .restaurant(reserveFound.getRestaurant())
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
        
        final var updated = reserveRepository.save(reserveEntity);
        
        return mapToDomain(updated);
    }

    private TableRestaurantEntity mapToEntity(final TableRestaurant tableRestaurant) {
        return TableRestaurantEntity.builder()
            .id(tableRestaurant.getId())
            .tableIdentification(tableRestaurant.getTableIdentification())
            .restaurant(RestaurantEntity.builder().id(tableRestaurant.getRestaurantId()).build())
            .reserve(ReserveEntity.builder().id(tableRestaurant.getReserveId()).build())
            .numberSeats(tableRestaurant.getNumberSeats())
            .statusTableOccupation(tableRestaurant.getStatusTableOccupation())
            .tablePosition(tableRestaurant.getTablePosition())
            .build();
    }

    private Reserve mapToDomain(final ReserveEntity entity) {
        return Reserve.builder()
            .id(entity.getId())
            .clientId(entity.getClient().getId())
            .restaurantId(entity.getRestaurant().getId())
            .tableRestaurants(entity.getTableRestaurant().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList()))
            .numberPeople(entity.getNumberPeople())
            .dateReserve(entity.getDateReserve())
            .dateCreated(entity.getDateCreated())
            .startReserve(entity.getStartReserve())
            .toleranceMinutes(entity.getToleranceMinutes())
            .timeLimit(entity.getTimeLimit())
            .statusReserve(entity.getStatusReserve())
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
}
