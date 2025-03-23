package com.fiap.techmesa.infrastructure.gateway;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.gateway.TableRestaurantGateway;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.TableRestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.AddressRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.RestaurantRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.TableRestaurantRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TableRestaurantGatewayImpl implements TableRestaurantGateway {

    private final TableRestaurantRepository tableRestaurantRepository;
    private final RestaurantRepository restaurantRepository;    
    @Override
    public TableRestaurant save(final TableRestaurant tableRestaurant) {
        TableRestaurantEntity tableRestaurantEntity = mapToEntity(tableRestaurant);
        TableRestaurantEntity savedTableRestaurantEntity = tableRestaurantRepository.save(tableRestaurantEntity);
        return mapToDomain(savedTableRestaurantEntity);
    }

    @Override
    public Pagination<TableRestaurant> findAll(Page page) {
        var pageRequest = PageRequest.of(page.page(), page.size());
        var tableRestaurantPage = tableRestaurantRepository.findAll(pageRequest);
        var tableRestaurants = tableRestaurantPage.getContent().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());

        return new Pagination<>(tableRestaurantPage.getNumber(), tableRestaurantPage.getSize(), tableRestaurantPage.getTotalPages(),
                tableRestaurantPage.getTotalElements(), tableRestaurantPage.getNumberOfElements(), tableRestaurants);
    }

    @Override
    public Optional<TableRestaurant> findById(final int id) {
        return tableRestaurantRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Optional<TableRestaurant> findByRestaurantAndDate(final String tableIdentification, final Integer reserveId) {
        return tableRestaurantRepository.findByRestaurantAndReserveDateReserve(
                RestaurantEntity.builder().id(reserveId).build(), LocalDate.now())
                .flatMap(list -> list.stream().findFirst())
                .map(this::mapToDomain);
    }

    @Override
    public Optional<TableRestaurant> findByRestaurantNotReservedAndDate(final int id, final StatusTableOccupationEnum statusTableOccupation, final LocalDate dateReserve) {
        return tableRestaurantRepository.findByRestaurantAndReserveDateReserve(
                RestaurantEntity.builder().id(id).build(), dateReserve)
                .flatMap(list -> list.stream()
                        .filter(table -> table.getStatusTableOccupation().equals(statusTableOccupation))
                        .findFirst())
                .map(this::mapToDomain);
    }

    @Transactional
    @Override
    public void delete(final String tableIdentification) {
        TableRestaurantEntity tableRestaurantEntity = tableRestaurantRepository.findByTableIdentification(tableIdentification);
        if (tableRestaurantEntity == null) {
            throw new IllegalArgumentException(String.format("Table with identification [%s] not found", tableIdentification));
        }
        tableRestaurantRepository.delete(tableRestaurantEntity);
    }

    @Override
    public TableRestaurant update(final TableRestaurant tableRestaurant) {
        final var tableRestaurantFound =
                tableRestaurantRepository.findById(tableRestaurant.getId());

        if (tableRestaurantFound == null) {
            throw new IllegalArgumentException(String.format("Table with identification [%s] not found", tableRestaurant.getTableIdentification()));
        }
        
        final var restaurant = restaurantRepository.findById(tableRestaurant.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Address with id [%s] not found", tableRestaurant.getRestaurantId())));
                
        final var tableRestaurantEntity =
                TableRestaurantEntity.builder()
            		    .id(tableRestaurant.getId())
                        .tableIdentification(tableRestaurant.getTableIdentification())
                        .restaurant(restaurant)
                        .reserve(tableRestaurant.getReserveId() != null ? ReserveEntity.builder().id(tableRestaurant.getReserveId()).build() : null)
                        .numberSeats(tableRestaurant.getNumberSeats())
                        .statusTableOccupation(tableRestaurant.getStatusTableOccupation())
                        .tablePosition(tableRestaurant.getTablePosition())
                        .build();

        final var updated = tableRestaurantRepository.save(tableRestaurantEntity);

        return mapToDomain(updated);
    }

    private TableRestaurantEntity mapToEntity(final TableRestaurant tableRestaurant) {
        return TableRestaurantEntity.builder()
                .tableIdentification(tableRestaurant.getTableIdentification())
                .restaurant(RestaurantEntity.builder().id(tableRestaurant.getRestaurantId()).build())
                .reserve(tableRestaurant.getReserveId() != null ? ReserveEntity.builder().id(tableRestaurant.getReserveId()).build() : null)
                .numberSeats(tableRestaurant.getNumberSeats())
                .statusTableOccupation(tableRestaurant.getStatusTableOccupation())
                .tablePosition(tableRestaurant.getTablePosition())
                .build();
    }

    public TableRestaurant mapToDomain(final TableRestaurantEntity entity) {
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
