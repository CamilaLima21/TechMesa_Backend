package com.fiap.techmesa.application.domain;

import java.time.LocalDate;
import java.util.List;

import com.fiap.techmesa.application.enums.StatusReserveEnum;

import jakarta.validation.constraints.Future;
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
public class Reserve {

    private Integer id;

    private Integer clientId; // Usando ID em vez da classe ClientEntity
    
    private Integer restaurantId; // Usando ID em vez da classe RestaurantEntity
    
    private List<TableRestaurant> tableRestaurants;

    private Integer numberPeople;

    private LocalDate dateReserve;
    
//    @Future(message = "Date created must be in the future")
    private LocalDate dateCreated;

    private LocalDate startReserve;

    private Integer toleranceMinutes;

    private LocalDate timeLimit;
    
    private StatusReserveEnum statusReserve;
    
    public static Reserve createReserve(
            final Integer clientId,
            final Integer restaurantId,
            final List<TableRestaurant> tableRestaurants,
            final Integer numberPeople,
            final LocalDate dateReserve,
            final LocalDate dateCreated,
            final LocalDate startReserve,
            final Integer toleranceMinutes,
            final LocalDate timeLimit,
            StatusReserveEnum statusReserve) {
        
        return new Reserve(null, clientId, restaurantId, tableRestaurants, numberPeople, dateReserve, dateCreated, startReserve, toleranceMinutes, timeLimit, statusReserve);
    }
}
