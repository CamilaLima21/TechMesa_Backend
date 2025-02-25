package com.fiap.techmesa.application.domain;

import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.enums.TablePositionEnum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TableRestaurant {
	
	@NotNull(message = "Table identification is required")
	private String tableIdentification;
	
	@NotNull(message = "Restaurant is required")
    private Restaurant restaurant;
	
    private Reserve reserve;
    
    private int numberSeats;
    
    private StatusTableOccupationEnum statusTableOccupation;
    
    private TablePositionEnum tablePosition;
    
    public static TableRestaurant createTableRestaurant(
    		final String tableIdentification,
    		final Restaurant restaurant,
    		final Reserve reserve,
    		final int numberSeats,
    		final StatusTableOccupationEnum statusTableOccupation,
    		final TablePositionEnum tablePosition) {
    	
    	return new TableRestaurant(tableIdentification, restaurant, reserve, numberSeats, statusTableOccupation, tablePosition);
    }
}
