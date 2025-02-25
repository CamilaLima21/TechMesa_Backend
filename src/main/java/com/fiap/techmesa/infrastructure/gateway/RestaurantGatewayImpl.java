package com.fiap.techmesa.infrastructure.gateway;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.application.enums.TypeKitchenEnum;
import com.fiap.techmesa.application.gateway.RestaurantGateway;

@Component
public class RestaurantGatewayImpl implements RestaurantGateway {

    @Override
    public Restaurant save(Restaurant restaurant) {
        // Implement the persistence of the restaurant
        return null;
    }

    @Override
    public Pagination<Restaurant> findAll(Page page) {
        // Implement the retrieval of all restaurants with pagination
        return null;
    }

    @Override
    public Optional<Restaurant> findById(int id) {
        // Implement the retrieval of a restaurant by ID
        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> findByTypeKitchen(TypeKitchenEnum typeKitchen) {
        // Implement the retrieval of a restaurant by type of kitchen
        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> findByPartName(String partName) {
        // Implement the retrieval of a restaurant by part of the name
        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> findByName(String name) {
        // Implement the retrieval of a restaurant by name
        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> findByEmail(String email) {
        // Implement the retrieval of a restaurant by email
        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> findByCity(String city) {
        // Implement the retrieval of a restaurant by city
        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> findByCityAndNeighborhood(String city, String neighborhood) {
        // Implement the retrieval of a restaurant by city and neighborhood
        return Optional.empty();
    }

    @Override
    public void delete(int id) {
        // Implement the deletion of a restaurant
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        // Implement the update of a restaurant
        return null;
    }
}