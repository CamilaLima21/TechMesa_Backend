package com.fiap.techmesa.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fiap.techmesa.application.enums.StatusReserveEnum;

import jakarta.persistence.*;
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
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ReserveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientEntity client;

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
