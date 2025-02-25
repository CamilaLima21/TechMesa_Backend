package com.fiap.techmesa.application.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAddressRequest {
	
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
    
    @NotBlank(message = "Cep is required")
	@Size(max = 100, message = "Cep length must be less than 100 characters")
    private String cep;

}
