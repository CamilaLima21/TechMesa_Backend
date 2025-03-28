package com.fiap.techmesa.application.domain;

import java.time.LocalDate;

import com.fiap.techmesa.application.enums.DayWeekEnum;
import com.fiap.techmesa.application.enums.TurnEnum;

import jakarta.validation.constraints.NotBlank;
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
	
	private Integer restaurantId;
	
	@NotBlank(message = "Name is required")
	@Size(max = 100, message = "Name length must be less than 100 characters")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Name must contain only letters, numbers and spaces")
	private String name;
	
    private TurnEnum turn;  
    private DayWeekEnum dayWeek;   
    private LocalDate startTime;
    private LocalDate endTime;
    
    public static OpeningHours createOpeningHours(
    		final Integer restaurantId,
    		final String name,
    		final TurnEnum turn,
    		final DayWeekEnum dayWeek,
    		final LocalDate startTime,
    		final LocalDate endTime) {
    	
    	return new OpeningHours(null, restaurantId, name, turn, dayWeek, startTime, endTime);
    }

}
