package com.fiap.techmesa.application.domain;

import java.time.LocalDate;
import java.util.List;

import com.fiap.techmesa.application.enums.StatusRestaurantEnum;
import com.fiap.techmesa.application.enums.TypeKitchenEnum;

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
public class Restaurant {
	
	private Integer id;
	
//	@NotBlank(message = "Name is required")
	@Size(max = 100, message = "Name length must be less than 100 characters")
	@Pattern(regexp = "[a-zA-Z\\s]+", message = "Name must contain only letters and spaces")
	private String name;
	
//	@NotBlank(message = "Address is required")
	@Size(max = 255, message = "Address length must be less than 255 characters")
    private Integer addressId;
   
//	@NotBlank(message = "Email is required")
	@Size(max = 255, message = "Email length must be less than 255 characters")
	@Email(message = "Email should be valid")
	private String email;
	
    private List<OpeningHours> openingHours;
    
    private List<Reserve> reserves;
    
    private List<TableRestaurant> tableRestaurants;
    
    private TypeKitchenEnum typeKitchen;
    
//    @NotNull(message = "Capacity is required")
    private int capacity;
    
    private StatusRestaurantEnum statusRestaurant;
    
    @Future(message = "Date must be in the future")
//    @NotNull(message = "Date is required")
    private LocalDate registrationDate;
    
    public static Restaurant createRestaurant(
    		final String name,
    		final Integer addressId,
    		final String email,
    		final List<OpeningHours> openingHours,
    		final List<Reserve> reserves,
    		final List<TableRestaurant> tableRestaurants,
    		final TypeKitchenEnum typeKitchen,
    		final int capacity,
    		final StatusRestaurantEnum statusRestaurant,
    		final LocalDate registrationDate) {
    	
    	return new Restaurant(null, name, addressId, email, openingHours, reserves, tableRestaurants, typeKitchen, capacity, statusRestaurant, registrationDate);
    }

}
