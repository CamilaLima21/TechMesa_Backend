package com.fiap.techmesa.application.gateway;

import java.time.LocalDate;
import java.util.Optional;

import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;

public interface TableRestaurantGateway {
	
	TableRestaurant save(final TableRestaurant buildDomain);
	
	Pagination<TableRestaurant> findAll(Page page);
	
	Optional<TableRestaurant> findById(final String tableIdentification);
	
	Optional<TableRestaurant> findByRestaurantAndDate(final String tableIdentification, final Reserve reserve);
	
	Optional<TableRestaurant> findByRestaurantNotReservedAndDate(final int id, final StatusTableOccupationEnum statusTableOccupation, final LocalDate dateReserve);
	
	void delete(final String tableIdentification);

	TableRestaurant update(TableRestaurant tableRestaurant);
	

}