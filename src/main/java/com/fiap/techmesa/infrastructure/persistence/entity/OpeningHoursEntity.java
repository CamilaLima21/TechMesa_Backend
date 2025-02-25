package com.fiap.techmesa.infrastructure.persistence.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.enums.DayWeekEnum;
import com.fiap.techmesa.application.enums.TurnEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "openingHours")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class OpeningHoursEntity {
	
	@Id
    @Column(unique = true)
	private long id;
	
	@ManyToOne
    @JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	@Column(name = "nome", nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	private TurnEnum turn;
	
	@Enumerated(EnumType.STRING)
	private DayWeekEnum dayWeek;
	
	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate startTime;
	
	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate endTime;

}
