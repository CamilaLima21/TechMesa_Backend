package com.fiap.techmesa.application.gateway;

import java.util.Optional;

import com.fiap.techmesa.application.domain.OpeningHours;

public interface OpeningHoursGateway {

	OpeningHours save(final OpeningHours openingHours);
	
	Optional<OpeningHours> findById(final int id);
	
	OpeningHours update(final OpeningHours openingHours);
	
	void delete(final int id);
}
