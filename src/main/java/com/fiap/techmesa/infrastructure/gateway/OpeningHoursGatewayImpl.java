package com.fiap.techmesa.infrastructure.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.gateway.OpeningHoursGateway;
import com.fiap.techmesa.infrastructure.persistence.entity.OpeningHoursEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.OpeningHoursRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OpeningHoursGatewayImpl implements OpeningHoursGateway {

    private final OpeningHoursRepository openingHoursRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public OpeningHours save(final OpeningHours openingHours) {
        OpeningHoursEntity openingHoursEntity = mapToEntity(openingHours);
        OpeningHoursEntity savedOpeningHoursEntity = openingHoursRepository.save(openingHoursEntity);
        return mapToDomain(savedOpeningHoursEntity);
    }

    @Override
    public Optional<OpeningHours> findById(final int id) {
        return openingHoursRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<OpeningHours> findAll() {
        return openingHoursRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public OpeningHours update(final OpeningHours openingHours) {
        final var openingHoursFound =
            openingHoursRepository.findById(openingHours.getId())
                .orElseThrow(() -> new IllegalArgumentException("Opening hours not found"));
        
        final var restaurant = restaurantRepository.findById(openingHours.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Restaurant with id [%s] not found", openingHours.getRestaurantId())));
        
        final var openingHoursEntity =
            OpeningHoursEntity.builder()
                .id(openingHoursFound.getId())
                .name(openingHours.getName())
                .turn(openingHours.getTurn())
                .dayWeek(openingHours.getDayWeek())
                .startTime(openingHours.getStartTime())
                .endTime(openingHours.getEndTime())
                .restaurant(restaurant)
                .build();

        final var updated = openingHoursRepository.save(openingHoursEntity);

        return mapToDomain(updated);
    }

    @Transactional
    @Override
    public void delete(final int id) {
        openingHoursRepository.deleteById(id);
    }

    
    
    private OpeningHoursEntity mapToEntity(final OpeningHours openingHours) {
    	
    	 final var restaurant = restaurantRepository.findById(openingHours.getRestaurantId())
                 .orElseThrow(() -> new IllegalArgumentException(
                         String.format("Restaurant with id [%s] not found", openingHours.getRestaurantId())));
         
    	
        return OpeningHoursEntity.builder()
            .name(openingHours.getName())
            .turn(openingHours.getTurn())
            .dayWeek(openingHours.getDayWeek())
            .startTime(openingHours.getStartTime())
            .endTime(openingHours.getEndTime())
            .restaurant(restaurant)
            .build();
    }

    private OpeningHours mapToDomain(final OpeningHoursEntity entity) {
        return OpeningHours.builder()
            .id(entity.getId())
            .name(entity.getName())
            .turn(entity.getTurn())
            .dayWeek(entity.getDayWeek())
            .startTime(entity.getStartTime())
            .endTime(entity.getEndTime())
            .restaurantId(entity.getRestaurant().getId())
            .build();
    }
}
