package com.fiap.techmesa.infrastructure.gateway;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.gateway.OpeningHoursGateway;
import com.fiap.techmesa.application.usecase.exception.OpeningHoursNotFoundException;
import com.fiap.techmesa.infrastructure.persistence.entity.OpeningHoursEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.OpeningHoursRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OpeningHoursGatewayImpl implements OpeningHoursGateway {
	
	private final OpeningHoursRepository openingHoursRepository;

    @Override
    public OpeningHours save(final OpeningHours request) {
    	final var entity =
    		OpeningHoursEntity.builder()
    			.name(request.getName())
    			.turn(request.getTurn())
    			.dayWeek(request.getDayWeek())
    			.startTime(request.getStartTime())
    			.endTime(request.getEndTime())
    			.build();
    	
    	final var saved = openingHoursRepository.save(entity);
    	
    	return this.toResponse(saved);
    }

    @Override
    public Optional<OpeningHours> findById(int id) {
        return openingHoursRepository.findById(id).map(this::toResponse);
    }

    @Override
    public OpeningHours update(final OpeningHours request) {
    	final var openingHoursFound =
    		openingHoursRepository
    			.findById(request.getId())
    			.orElseThrow(() -> new OpeningHoursNotFoundException(request.getId()));
    	
    	final var newEntity =
    		OpeningHoursEntity.builder()
    			.id(openingHoursFound.getId())
    			.restaurant(openingHoursFound.getRestaurant())
    			.name(openingHoursFound.getName())
    			.turn(openingHoursFound.getTurn())
    			.dayWeek(openingHoursFound.getDayWeek())
    			.startTime(openingHoursFound.getStartTime())
    			.endTime(openingHoursFound.getEndTime())
    			.build();
    	
    	final var updated = openingHoursRepository.save(newEntity);
    	
    	return this.toResponse(updated);
    }

    @Transactional
    @Override
    public void delete(final int id) {
    	openingHoursRepository.deleteById(id);
    }
    
    private OpeningHours toResponse(final OpeningHoursEntity entity) {
    	return new OpeningHours(
    			entity.getId(),
    			entity.getName(),
    			entity.getTurn(),
    			entity.getDayWeek(),
    			entity.getStartTime(),
    			entity.getEndTime());
    }
}