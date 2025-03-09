package com.fiap.techmesa.application.domain;

import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.enums.TablePositionEnum;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;

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
	
	private Integer id;
	
//	@NotNull(message = "Table identification is required")
	private String tableIdentification;
	
//	@NotNull(message = "Restaurant is required")
    private Integer restaurantId;
	
    private Integer reserveId;
    
    private int numberSeats;
    
    private StatusTableOccupationEnum statusTableOccupation;
    
    private TablePositionEnum tablePosition;
    
    public static TableRestaurant createTableRestaurant(
    		final String tableIdentification,
    		final Integer restaurantId,
    		final Integer reserveId,
    		final int numberSeats,
    		final StatusTableOccupationEnum statusTableOccupation,
    		final TablePositionEnum tablePosition) {
    	
    	return new TableRestaurant(null, tableIdentification, restaurantId, reserveId, numberSeats, statusTableOccupation, tablePosition);
    }


	}
