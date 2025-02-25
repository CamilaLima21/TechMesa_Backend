package com.fiap.techmesa.application.dto;


import java.time.LocalDate;

import com.fiap.techmesa.application.enums.DayWeekEnum;
import com.fiap.techmesa.application.enums.TurnEnum;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateOpeningHoursRequest {

	private TurnEnum turn;
    
    private DayWeekEnum dayWeek;
    
    @Future(message = "Date must be in the future")
    @NotNull(message = "Date cannot be null")
    private LocalDate startTime;
    
    @Future(message = "Date must be in the future")
    @NotNull(message = "Date cannot be null")
    private LocalDate endTime;
}
