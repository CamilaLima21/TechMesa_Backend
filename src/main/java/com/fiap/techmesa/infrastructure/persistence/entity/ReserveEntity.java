package com.fiap.techmesa.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.enums.StatusReserveEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reserve")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ReserveEntity {

	@Id
	@Column(unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
	private ClientEntity client;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
	private RestaurantEntity restaurant;
	
	@OneToMany(mappedBy = "reserve", fetch = FetchType.LAZY)
	private List<TableRestaurantEntity> tableRestaurant;
	
	@Column
	private int numberPeople;
	
	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate dateReserve;
	
	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate dateCreated;
	
	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate startReserve;
	
	@Column
	private int toleranceMinutes;
	
	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate timeLimit;
	
	@Column
	private StatusReserveEnum statusReserve;
}
