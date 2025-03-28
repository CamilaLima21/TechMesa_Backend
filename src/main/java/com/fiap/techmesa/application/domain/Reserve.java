package com.fiap.techmesa.application.domain;

import java.time.LocalDate;

import com.fiap.techmesa.application.enums.StatusReserveEnum;

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

    private Integer clientId;
    
    private Integer restaurantId;
    
    private Integer tableRestaurants;

    private Integer numberPeople;

    private LocalDate dateReserve;
    
    private LocalDate dateCreated;

    private LocalDate startReserve;

    private Integer toleranceMinutes;

    private LocalDate timeLimit;
    
    private StatusReserveEnum statusReserve;
    
    public static Reserve createReserve(
            final Integer clientId,
            final Integer restaurantId,
            final Integer tableRestaurants,
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
