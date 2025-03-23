package com.fiap.techmesa.infrastructure.persistence.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fiap.techmesa.application.enums.DayWeekEnum;
import com.fiap.techmesa.application.enums.TurnEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "opening_hours")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class OpeningHoursEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private RestaurantEntity restaurant;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "turn", nullable = false)
    private TurnEnum turn;

    @Column(name = "day_week", nullable = false)
    private DayWeekEnum dayWeek;

    @Column(name = "start_time", nullable = false, columnDefinition = "DATE")
    private LocalDate startTime;

    @Column(name = "end_time", nullable = false, columnDefinition = "DATE")
    private LocalDate endTime;
}
