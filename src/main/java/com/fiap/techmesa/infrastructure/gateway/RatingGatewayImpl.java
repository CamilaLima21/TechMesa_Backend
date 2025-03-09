package com.fiap.techmesa.infrastructure.gateway;

import static java.lang.String.format;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techmesa.application.domain.Rating;
import com.fiap.techmesa.application.gateway.RatingGateway;
import com.fiap.techmesa.application.usecase.exception.RatingNotFoundException;
import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RatingEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.ClientRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.RatingRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RatingGatewayImpl implements RatingGateway {
	
	private final RatingRepository ratingRepository;
	private final ClientRepository clientRepository;

    @Override
    public Rating save(final Rating rating) {
    	final Optional<ClientEntity> clientEntityFound =
    		clientRepository.findById(rating.getId());
    	
    	if (clientEntityFound.isEmpty()) {
    	      throw new IllegalArgumentException(
    	          format("Client with id [%s] not found", rating.getClientId()));
    	    }
    	   	
    	final var clientEntity = clientEntityFound.get();
    	
    	final var ratingEntity =
    		RatingEntity.builder()
    			.client(clientEntity)
    			.title(rating.getTitle())
    			.text(rating.getText())
    			.note(rating.getNote())
    			.dateRegistration(rating.getDateRegistration())
    			.build();
    	
    	final var saved = ratingRepository.save(ratingEntity);
    	
    	return this.toResponse(saved);
    }


	@Override
    public Optional<Rating> findById(int id) {
        return ratingRepository.findById(id).map(this::toResponse);
    }

    @Override
    public Rating update(final Rating rating) {
    	final var ratingFound =
    		ratingRepository
    			.findById(rating.getId())
    			.orElseThrow(() -> new RatingNotFoundException(rating.getId()));
    	
    	final var ratingEntity =
    		RatingEntity.builder()
    			.id(ratingFound.getId())
    			.client(ratingFound.getClient())
    			.title(ratingFound.getTitle())
    			.text(ratingFound.getText())
    			.note(ratingFound.getNote())
    			.dateRegistration(ratingFound.getDateRegistration())
    			.build();
    	
    	final var updated = ratingRepository.save(ratingEntity);
    	
    	return this.toResponse(updated);
    }

    @Transactional
    @Override
    public void delete(final int id) {
        ratingRepository.deleteById(id);
    }
    
    private Rating toResponse(final RatingEntity entity) {
    	return new Rating(
    		entity.getId(),
    		entity.getClient().getId(),
    		entity.getTitle(),
    		entity.getText(),
    		entity.getNote(),
    		entity.getDateRegistration());
    }
}