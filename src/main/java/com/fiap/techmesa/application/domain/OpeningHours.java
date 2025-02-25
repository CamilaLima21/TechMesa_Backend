package com.fiap.techmesa.application.domain;

import java.time.LocalDate;

import com.fiap.techmesa.application.enums.DayWeekEnum;
import com.fiap.techmesa.application.enums.TurnEnum;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class OpeningHours {
	
	private Integer id;
	
	@NotBlank(message = "Name is required")
	@Size(max = 100, message = "Name length must be less than 100 characters")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Name must contain only letters,numbers and spaces")
	private String name;
	
    private TurnEnum turn;
    
    private DayWeekEnum dayWeek;
    
    @Future(message = "Date must be in the future")
    @NotNull(message = "Date cannot be null")
    private LocalDate startTime;
    
    @Future(message = "Date must be in the future")
    @NotNull(message = "Date cannot be null")
    private LocalDate endTime;
    
    public static OpeningHours createOpeningHours(
    		final String name,
    		final TurnEnum turn,
    		final DayWeekEnum dayWeek,
    		final LocalDate startTime,
    		final LocalDate endTime) {
    	
    	return new OpeningHours(null, name, turn, dayWeek, startTime, endTime);
    }
    
    
}
