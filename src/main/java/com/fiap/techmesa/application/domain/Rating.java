package com.fiap.techmesa.application.domain;

import java.time.LocalDate;

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
public class Rating {
	
	private Integer id;
	
	private Integer clientId;

	@Size(max = 100, message = "Title length must be less than 100 characters")
	@Pattern(regexp = "[a-zA-Z\\s]+", message = "Title must contain only letters and spaces")
	private String title;
	
	@Size(max = 500, message = "Text length must be less than 500 characters")
    private String text;
	
	@NotNull(message = "Note id cannot be null")
    private int note;
	

    private LocalDate dateRegistration;
       
    public static Rating createRating(
    		final Integer clientId,
    		final String title,
    		final String text,
    		final int note,
    		final LocalDate dateRegistration) {
    	
    	return new Rating(null, clientId, title, text, note, dateRegistration);
    }

}
