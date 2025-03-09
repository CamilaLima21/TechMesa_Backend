package com.fiap.techmesa.application.gateway;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;

public interface ReserveGateway {
	
	Reserve save(final Reserve reserve);
	
	Pagination<Reserve> findAll(Page page);
	
	Optional<Reserve> findById(final int id);
	
	Optional<List<ReserveEntity>> findByRestaurantIdAndDate(final Integer restaurantId, final LocalDate dateReserve);
	
	Optional<List<ReserveEntity>> findByRestaurantIdAndClientIdAndData(final Integer restaurantId, final Integer clientId, final LocalDate dateReserve);
	
	void delete(final int id);

	Reserve update(final Reserve reserve);

}