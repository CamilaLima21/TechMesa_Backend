package com.fiap.techmesa.application.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class Address {
	
	private Integer id;
	
	@NotBlank(message = "Street is required")
	@Size(max = 100, message = "Street length must be less than 100 characters")
	private String street;
	
    private int number;
    
    @NotBlank(message = "Neighborhood is required")
	@Size(max = 100, message = "Neighborhood length must be less than 100 characters")
    private String neighborhood;
    
    @NotBlank(message = "City is required")
	@Size(max = 100, message = "City length must be less than 100 characters")
    private String city;
    
    @NotBlank(message = "State is required")
	@Size(max = 100, message = "State length must be less than 100 characters")
    private String state;
    
    @NotBlank(message = "Country is required")
	@Size(max = 100, message = "Country length must be less than 100 characters")
    private String country;
    
    @NotBlank(message = "ZipCode is required")
	@Size(max = 12, message = "ZipCode length must be less than 12 characters")
    private String zipCode;
    
    public static Address createAddress(
    		final String street,
    		final int number,
    		final String neighborhood,
    		final String city,
    		final String state,
    		final String country,
    		final String zipCode) {
    	
    	return new Address(null, street, number, neighborhood, city, state, country, zipCode);
    }

}
