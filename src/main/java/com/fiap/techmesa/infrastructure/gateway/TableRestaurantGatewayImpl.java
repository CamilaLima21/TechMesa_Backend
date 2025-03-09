package com.fiap.techmesa.infrastructure.gateway;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.gateway.TableRestaurantGateway;

@Component
public class TableRestaurantGatewayImpl implements TableRestaurantGateway {

    @Override
    public TableRestaurant save(TableRestaurant tableRestaurant) {
        // Implement the persistence of the table restaurant
        return null;
    }

    @Override
    public Pagination<TableRestaurant> findAll(Page page) {
        // Implement the retrieval of all table restaurants with pagination
        return null;
    }

    @Override
    public Optional<TableRestaurant> findById(String tableIdentification) {
        // Implement the retrieval of a table restaurant by ID
        return Optional.empty();
    }

    @Override
    public Optional<TableRestaurant> findByRestaurantAndDate(String tableIdentification, Integer reserve) {
        // Implement the retrieval of a table restaurant by restaurant and date
        return Optional.empty();
    }

    @Override
    public Optional<TableRestaurant> findByRestaurantNotReservedAndDate(int id, StatusTableOccupationEnum statusTableOccupation, LocalDate dateReserve) {
        // Implement the retrieval of a table restaurant by restaurant, not reserved, and date
        return Optional.empty();
    }

    @Override
    public void delete(String tableIdentification) {
        // Implement the deletion of a table restaurant
    }

    @Override
    public TableRestaurant update(TableRestaurant tableRestaurant) {
        // Implement the update of a table restaurant
        return null;
    }
}