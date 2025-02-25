package com.fiap.techmesa.application.gateway;

import java.util.Optional;

import com.fiap.techmesa.application.domain.Rating;

public interface RatingGateway {
	
	Rating save(final Rating rating);
	
	Optional<Rating> findById(final int id);
	
	Rating update(final Rating rating);
	
	void delete(final int id);

}
