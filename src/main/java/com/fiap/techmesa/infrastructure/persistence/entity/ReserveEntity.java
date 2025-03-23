package com.fiap.techmesa.infrastructure.persistence.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fiap.techmesa.application.enums.StatusReserveEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reserve")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonSerialize(using = ReserveEntitySerializer.class)
public class ReserveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;
    
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientEntity client;
    
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private RestaurantEntity restaurant;
    
    @OneToOne(mappedBy = "reserve", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TableRestaurantEntity tableRestaurant;

    @Column(name = "number_people", nullable = false)
    private int numberPeople;

    @Column(name = "date_reserve", nullable = false, columnDefinition = "DATE")
    private LocalDate dateReserve;

    @Column(name = "date_created", nullable = false, columnDefinition = "DATE")
    private LocalDate dateCreated;

    @Column(name = "start_reserve", nullable = false, columnDefinition = "DATE")
    private LocalDate startReserve;

    @Column(name = "tolerance_minutes")
    private Integer toleranceMinutes;

    @Column(name = "time_limit", nullable = false, columnDefinition = "DATE")
    private LocalDate timeLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_reserve", nullable = false)
    private StatusReserveEnum statusReserve;
}
