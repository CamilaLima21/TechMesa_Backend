package com.fiap.techmesa.application.domain;

import java.time.LocalDate;
import java.util.List;

import com.fiap.techmesa.application.enums.StatusReserveEnum;
import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.TableRestaurantEntity;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reserve {

	private Integer id;
	
	@NotNull(message = "Client id cannot be null")
	private Client client;
	
	@NotNull(message = "Restaurant id cannot be null")
    private Restaurant restaurant;
    
    private List<TableRestaurant> tableRestaurants;
    
    @NotNull(message = "Number people cannot be null")
    private Integer numberPeople;
    
    @NotNull(message = "Date reserve cannot be null")
    private LocalDate dateReserve;
    
    @Future(message = "Date created must be in the future")
    @NotNull(message = "Date created cannot be null")
    private LocalDate dateCreated;
    
    @NotNull(message = "Start reserve cannot be null")
    private LocalDate startReserve;
    
    private Integer toleranceMinutes;
    
    private LocalDate timeLimit;
    
    private StatusReserveEnum statusReserve;
    
    public static Reserve createReserve(
    		final Client client,
    		final Restaurant restaurant,
    		final List<TableRestaurant> tableRestaurants,
    		final Integer numberPeople,
    		final LocalDate dateReserve,
    		final LocalDate dateCreated,
    		final LocalDate startReserve,
    		final Integer toleranceMinutes,
    		final LocalDate timeLimit,
    		StatusReserveEnum statusReserve) {
    	
    	return new Reserve(null, client, restaurant, tableRestaurants, numberPeople, dateReserve, dateCreated, startReserve, toleranceMinutes, timeLimit, statusReserve);
    	
    }

    
}
