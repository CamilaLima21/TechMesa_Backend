package com.fiap.techmesa.application.dto;


import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.enums.TablePositionEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateTableRestaurantRequest {
	
	private Reserve reserve;
    
    private int numberSeats;
    
    private StatusTableOccupationEnum statusTableOccupation;
    
    private TablePositionEnum tablePosition;

}
