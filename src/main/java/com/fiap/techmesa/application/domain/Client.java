package com.fiap.techmesa.application.domain;

import java.time.LocalDate;
import java.util.List;


import jakarta.validation.constraints.Email;
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
public class Client {

	private Integer id;
	
	@NotBlank(message = "Name is required")
	@Size(max = 100, message = "Name length must be less than 100 characters")
	@Pattern(regexp = "[a-zA-Z\\s]+", message = "Name must contain only letters and spaces")
	private String name;
	
	@NotBlank(message = "Email is required")
	@Size(max = 255, message = "Email length must be less than 255 characters")
	@Email(message = "Email should be valid")
    private String email;
	
	@Future(message = "Date must be in the future")
	@NotNull(message = "Date is required")
    private LocalDate registrationDate;
	
	@NotBlank(message = "Address is required")
	@Size(max = 100, message = "Address length must be less than 100 characters")
    private Address address;
    
    private List<Reserve> reserves;
        
    public static Client createClient(
    		final String name,
    		final String email,
    		final LocalDate registrationDate,
    		final Address address,
    		final List<Reserve> reserves) {
				return new Client(null, name, email, registrationDate, address, reserves);
    	   	
    }
}
