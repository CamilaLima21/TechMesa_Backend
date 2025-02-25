package com.fiap.techmesa.application.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateReserveRequest {

	@NotNull(message = "Number people cannot be null")
    private int numberPeople;
    
    @NotNull(message = "Date reserve cannot be null")
    private LocalDate dateReserve;
    
    @NotNull(message = "Start reserve cannot be null")
    private LocalDate startReserve;
}
