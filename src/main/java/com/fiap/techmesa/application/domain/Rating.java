package com.fiap.techmesa.application.domain;

import java.time.LocalDate;

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
public class Rating {
	
	private Integer id;

	@NotBlank(message = "Title is required")
	@Size(max = 100, message = "Title length must be less than 100 characters")
	@Pattern(regexp = "[a-zA-Z\\s]+", message = "Title must contain only letters and spaces")
	private String title;
	
	@NotBlank(message = "Text is required")
	@Size(max = 500, message = "Text length must be less than 500 characters")
    private String text;
	
	@NotNull(message = "Note id cannot be null")
    private int note;
	
	@Future(message = "Date must be in the future")
	@NotNull(message = "Date cannot be null")
    private LocalDate dateRegistration;
	
    private Client client;
    
    public static Rating createRating(
    		final String title,
    		final String text,
    		final int note,
    		final LocalDate dateRegistration,
    		final Client client) {
    	
    	return new Rating(null, title, text, note, dateRegistration, client);
    }
}
