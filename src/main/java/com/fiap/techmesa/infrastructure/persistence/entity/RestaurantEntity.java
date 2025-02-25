package com.fiap.techmesa.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.enums.StatusRestaurantEnum;
import com.fiap.techmesa.application.enums.TypeKitchenEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class RestaurantEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
	private long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)  
	List<OpeningHours> openingHours;
	
	@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)  
	List<Reserve> reserve;
	
	@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)  
	List<TableRestaurant> tableRestaurant;
	
	@Column
	private TypeKitchenEnum typeKitchen;
	
	@Column
	private int capacity;
	
	@Column
	private StatusRestaurantEnum statusRestaurant;
	
	 @Column(nullable = false, columnDefinition = "DATE")
	private LocalDate registrationDate;
}
