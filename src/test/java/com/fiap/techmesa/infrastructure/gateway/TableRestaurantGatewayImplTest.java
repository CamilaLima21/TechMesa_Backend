package com.fiap.techmesa.infrastructure.gateway;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.enums.TablePositionEnum;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.TableRestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.RestaurantRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.TableRestaurantRepository;

public class TableRestaurantGatewayImplTest {

    @Mock
    private TableRestaurantRepository tableRestaurantRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private TableRestaurantGatewayImpl tableRestaurantGateway;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
        TableRestaurant tableRestaurant = createTableRestaurant();
        TableRestaurantEntity tableRestaurantEntity = new TableRestaurantEntity();
        TableRestaurantGatewayImpl spyTableRestaurantGateway = spy(tableRestaurantGateway);
        when(tableRestaurantRepository.save(any(TableRestaurantEntity.class))).thenReturn(tableRestaurantEntity);
        doReturn(tableRestaurant).when(spyTableRestaurantGateway).mapToDomain(any());
        TableRestaurant result = spyTableRestaurantGateway.save(tableRestaurant);
        assertNotNull(result);
    }

    @Test
    void findAllTest() {
        var pageRequest = PageRequest.of(0, 10);
        var tableRestaurantEntity = new TableRestaurantEntity();
        var tableRestaurantPage = new PageImpl<>(List.of(tableRestaurantEntity));
        when(tableRestaurantRepository.findAll(pageRequest)).thenReturn(tableRestaurantPage);

        TableRestaurantGatewayImpl spyTableRestaurantGateway = spy(tableRestaurantGateway);
        doReturn(createTableRestaurant()).when(spyTableRestaurantGateway).mapToDomain(any(TableRestaurantEntity.class));

        var page = new Page(0, 10);
        var result = spyTableRestaurantGateway.findAll(page);
        assertNotNull(result);
    }

    @Test
    void findByIdTest() {
        TableRestaurantEntity tableRestaurantEntity = new TableRestaurantEntity();
        when(tableRestaurantRepository.findById(anyInt())).thenReturn(Optional.of(tableRestaurantEntity));

        TableRestaurantGatewayImpl spyTableRestaurantGateway = spy(tableRestaurantGateway);
        doReturn(createTableRestaurant()).when(spyTableRestaurantGateway).mapToDomain(any(TableRestaurantEntity.class));

        var result = spyTableRestaurantGateway.findById(1);
        assertNotNull(result);
    }

    @Test
    void findByRestaurantAndDateTest() {
        TableRestaurantEntity tableRestaurantEntity = new TableRestaurantEntity();
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1);
        tableRestaurantEntity.setRestaurant(restaurantEntity);
        when(tableRestaurantRepository.findByRestaurantAndReserveDateReserve(any(RestaurantEntity.class), any(LocalDate.class)))
                .thenReturn(Optional.of(List.of(tableRestaurantEntity)));

        TableRestaurantGatewayImpl spyTableRestaurantGateway = spy(tableRestaurantGateway);
        doReturn(createTableRestaurant()).when(spyTableRestaurantGateway).mapToDomain(any(TableRestaurantEntity.class));

        var result = spyTableRestaurantGateway.findByRestaurantAndDate("Table 1", 1);
        assertNotNull(result);
    }

    @Test
    void deleteTest() {
        TableRestaurantEntity tableRestaurantEntity = new TableRestaurantEntity();
        when(tableRestaurantRepository.findByTableIdentification(anyString())).thenReturn(tableRestaurantEntity);
        doNothing().when(tableRestaurantRepository).delete(any(TableRestaurantEntity.class));

        tableRestaurantGateway.delete("Table 1");
        verify(tableRestaurantRepository, times(1)).delete(tableRestaurantEntity);
    }

    @Test
    void updateTest() {
        TableRestaurant tableRestaurant = createTableRestaurant();
        TableRestaurantEntity tableRestaurantEntity = new TableRestaurantEntity();
        when(tableRestaurantRepository.findById(anyInt())).thenReturn(Optional.of(tableRestaurantEntity));
        when(restaurantRepository.findById(anyInt())).thenReturn(Optional.of(new RestaurantEntity()));
        when(tableRestaurantRepository.save(any(TableRestaurantEntity.class))).thenReturn(tableRestaurantEntity);

        TableRestaurantGatewayImpl spyTableRestaurantGateway = spy(tableRestaurantGateway);
        doReturn(tableRestaurant).when(spyTableRestaurantGateway).mapToDomain(any());

        TableRestaurant updatedTableRestaurant = spyTableRestaurantGateway.update(tableRestaurant);
        assertNotNull(updatedTableRestaurant);
    }

    private TableRestaurant createTableRestaurant() {
        return TableRestaurant.builder()
                .id(1)
                .tableIdentification("Table 1")
                .restaurantId(1)
                .reserveId(1)
                .numberSeats(4)
                .statusTableOccupation(StatusTableOccupationEnum.AVAILABLE)
                .tablePosition(TablePositionEnum.EXTERNAL)
                .build();
    }
}