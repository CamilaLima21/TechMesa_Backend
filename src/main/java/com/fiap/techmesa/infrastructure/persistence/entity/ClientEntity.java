package com.fiap.techmesa.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name= "client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ClientEntity {

	@Id
	@Column(unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private AddressEntity address;
	
	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
	private List<ReserveEntity> reserve;
	
	@Column
	private String name;
	
	@Column(unique = true)
	private String email;
	
	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate registrationDate;
	
	
}
