package com.fiap.techmesa.infrastructure.gateway;

import static java.lang.String.format;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                format("Client with id [%s] not found", reserve.getClientId()));
        }
        
        final Optional<RestaurantEntity> restaurantEntityFound =
            restaurantRepository.findById(reserve.getRestaurantId());
        
        if (restaurantEntityFound.isEmpty()) {
            throw new IllegalArgumentException(
                format("Restaurant with id [%s] not found", reserve.getRestaurantId()));
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
        
        return this.toResponse(saved);
    }

    @Override
    public Optional<Reserve> findById(final int id) {
        return reserveRepository.findById(id).map(this::toResponse);
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
            reserveRepository
                .findById(reserve.getId())
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
        
        return this.toResponse(updated);
    }

    private TableRestaurantEntity mapToEntity(final TableRestaurant tableRestaurant) {
        return TableRestaurantEntity.builder()
            .id(tableRestaurant.getId())
            .tableIdentification(tableRestaurant.getTableIdentification())
            .restaurant(restaurantRepository.findById(tableRestaurant.getRestaurantId()).orElseThrow())
            .reserve(reserveRepository.findById(tableRestaurant.getReserveId()).orElseThrow())
            .numberSeats(tableRestaurant.getNumberSeats())
            .statusTableOccupation(tableRestaurant.getStatusTableOccupation())
            .tablePosition(tableRestaurant.getTablePosition())
            .build();
    }

    private ReserveEntity mapToEntity(final Reserve reserve) {
        return ReserveEntity.builder()
            .id(reserve.getId())
            .client(clientRepository.findById(reserve.getClientId()).orElseThrow())
            .restaurant(restaurantRepository.findById(reserve.getRestaurantId()).orElseThrow())
            .tableRestaurant(reserve.getTableRestaurants().stream()
                    .map(this::mapToEntity)
                    .collect(Collectors.toList()))
            .numberPeople(reserve.getNumberPeople())
            .dateReserve(reserve.getDateReserve())
            .dateCreated(reserve.getDateCreated())
            .startReserve(reserve.getStartReserve())
            .toleranceMinutes(reserve.getToleranceMinutes())
            .timeLimit(reserve.getTimeLimit())
            .statusReserve(reserve.getStatusReserve())
            .build();
    }

    private Reserve toResponse(final ReserveEntity entity) {
        return new Reserve(
            entity.getId(),
            entity.getClient().getId(),
            entity.getRestaurant().getId(),
            entity.getTableRestaurant().stream()
                .map(this::toResponse)
                .collect(Collectors.toList()),
            entity.getNumberPeople(),
            entity.getDateReserve(),
            entity.getDateCreated(),
            entity.getStartReserve(),
            entity.getToleranceMinutes(),
            entity.getTimeLimit(),
            entity.getStatusReserve());
    }

    private TableRestaurant toResponse(final TableRestaurantEntity entity) {
        return new TableRestaurant(
            entity.getId(),
            entity.getTableIdentification(),
            entity.getRestaurant().getId(),
            entity.getReserve().getId(),
            entity.getNumberSeats(),
            entity.getStatusTableOccupation(),
            entity.getTablePosition());
    }

	@Override
	public Pagination<Reserve> findAll(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
}
