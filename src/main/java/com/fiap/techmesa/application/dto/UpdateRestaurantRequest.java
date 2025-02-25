package com.fiap.techmesa.application.dto;

import java.util.List;

import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.enums.TypeKitchenEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateRestaurantRequest {
	
	@NotBlank(message = "Name is required")
	@Size(max = 100, message = "Name length must be less than 100 characters")
	@Pattern(regexp = "[a-zA-Z\\s]+", message = "Name must contain only letters and spaces")
	private String name;
	
	@NotBlank(message = "Email is required")
	@Size(max = 255, message = "Email length must be less than 255 characters")
	@Email(message = "Email should be valid")
	private String email;
	
    private List<OpeningHours> openingHours;
    
    private TypeKitchenEnum typeKitchen;
    
    @NotNull(message = "Capacity is required")
    private int capacity;

}
