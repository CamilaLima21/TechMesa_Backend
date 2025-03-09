package com.fiap.techmesa.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fiap.techmesa.application.enums.StatusRestaurantEnum;
import com.fiap.techmesa.application.enums.TypeKitchenEnum;

import jakarta.persistence.*;
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
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<OpeningHoursEntity> openingHours;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<ReserveEntity> reserve;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<TableRestaurantEntity> tableRestaurant;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "type_kitchen")
    private TypeKitchenEnum typeKitchen;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "status_restaurant")
    private StatusRestaurantEnum statusRestaurant;

    @Column(name = "registration_date", nullable = false, columnDefinition = "DATE")
    private LocalDate registrationDate;
}
