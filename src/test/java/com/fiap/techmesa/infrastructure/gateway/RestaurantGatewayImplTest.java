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

import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.application.enums.DayWeekEnum;
import com.fiap.techmesa.application.enums.StatusReserveEnum;
import com.fiap.techmesa.application.enums.StatusRestaurantEnum;
import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.enums.TablePositionEnum;
import com.fiap.techmesa.application.enums.TurnEnum;
import com.fiap.techmesa.application.enums.TypeKitchenEnum;
import com.fiap.techmesa.infrastructure.persistence.entity.AddressEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.OpeningHoursEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.TableRestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.AddressRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.RestaurantRepository;

public class RestaurantGatewayImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private RestaurantGatewayImpl restaurantGateway;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
    	Restaurant restaurant = createRestaurant();
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        RestaurantGatewayImpl spyRestaurantGateway = spy(restaurantGateway);
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurantEntity);
        doReturn(restaurant).when(spyRestaurantGateway).mapToDomain(any());
        Restaurant result = spyRestaurantGateway.save(restaurant);
        assertNotNull(result);
    }
    
    @Test
    void findByCityAndNeighborhoodTest() {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1); 
        restaurantEntity.setAddress(addressEntity); 
        OpeningHoursEntity openingHour = new OpeningHoursEntity();
        openingHour.setRestaurant(restaurantEntity); 
        List<OpeningHoursEntity> openingHours = List.of(openingHour); 
        restaurantEntity.setOpeningHours(openingHours);
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1); 
        TableRestaurantEntity tableRestaurantEntity = new TableRestaurantEntity();
        tableRestaurantEntity.setId(1);
        tableRestaurantEntity.setRestaurant(restaurantEntity);
        ReserveEntity reserveEntity = new ReserveEntity();
        reserveEntity.setClient(clientEntity); 
        reserveEntity.setRestaurant(restaurantEntity);
        reserveEntity.setTableRestaurant(tableRestaurantEntity); 
        List<ReserveEntity> reserves = List.of(reserveEntity); 
        restaurantEntity.setReserve(reserves);

        List<TableRestaurantEntity> tableRestaurants = List.of(tableRestaurantEntity); 
        restaurantEntity.setTableRestaurant(tableRestaurants);

        when(restaurantRepository.findByCityAndNeighborhood(anyString(), anyString())).thenReturn(Optional.of(List.of(restaurantEntity)));

        var result = restaurantGateway.findByCityAndNeighborhood("city", "neighborhood");
        assertNotNull(result);
    }

    @Test
    void deleteTest() {
        doNothing().when(restaurantRepository).deleteById(anyInt());

        restaurantGateway.delete(1);
        verify(restaurantRepository, times(1)).deleteById(1);
    }
    
    @Test void testUpdate() {

    	Restaurant restaurant = createRestaurant();
    	
    	RestaurantEntity restaurantEntity = new RestaurantEntity();
    	restaurantEntity.setId(1);
    	restaurantEntity.setName("Original Restaurant");

    	AddressEntity addressEntity = new AddressEntity();
    	addressEntity.setId(1);
    	restaurantEntity.setAddress(addressEntity);

    	when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurantEntity));
    	when(addressRepository.findById(1)).thenReturn(Optional.of(addressEntity));
    	when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(restaurantEntity);

    	RestaurantGatewayImpl spyRestaurantGateway2 = spy(restaurantGateway);

    	doReturn(restaurant).when(spyRestaurantGateway2).mapToDomain(any());

    	Restaurant updatedRestaurant = spyRestaurantGateway2.update(restaurant);

    	assertNotNull(updatedRestaurant);
    }
    
    
    @Test
    void testFindAll() {
  	
    	var pageRequest = PageRequest.of(0, 10);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1);
        restaurantEntity.setName("Test Restaurant");
        var restaurantEntities = List.of(restaurantEntity);
        var restaurantPage = new PageImpl<>(restaurantEntities, pageRequest, restaurantEntities.size());
        
        when(restaurantRepository.findAll(pageRequest)).thenReturn(restaurantPage);
        RestaurantGatewayImpl spyRestaurantGateway = spy(restaurantGateway);
        Restaurant restaurant = Restaurant.builder()
                .id(1)
                .name("Test Restaurant")
                .build();
        doReturn(restaurant).when(spyRestaurantGateway).mapToDomain(any(RestaurantEntity.class));
        Pagination<Restaurant> result = spyRestaurantGateway.findAll(new Page(0, 10));
        assertNotNull(result);
    }

    
    public Restaurant createRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        restaurant.setName("Restaurant Name");
        restaurant.setAddressId(1);
        restaurant.setEmail("email@example.com");
        restaurant.setTypeKitchen(TypeKitchenEnum.ITALIAN);
        restaurant.setCapacity(100);
        restaurant.setStatusRestaurant(StatusRestaurantEnum.ACTIVE);
        restaurant.setRegistrationDate(LocalDate.now());

        OpeningHours openingHours = new OpeningHours();
        openingHours.setId(1);
        openingHours.setRestaurantId(1);
        openingHours.setName("Opening Hours");
        openingHours.setTurn(TurnEnum.LUNCH);
        openingHours.setDayWeek(DayWeekEnum.MONDAY);
        openingHours.setStartTime(LocalDate.now());
        openingHours.setEndTime(LocalDate.now());
        restaurant.setOpeningHours(List.of(openingHours));

        Reserve reserve = new Reserve();
        reserve.setId(1);
        reserve.setClientId(1);
        reserve.setRestaurantId(1);
        reserve.setTableRestaurants(1);
        reserve.setNumberPeople(4);
        reserve.setDateReserve(LocalDate.now());
        reserve.setDateCreated(LocalDate.now());
        reserve.setStartReserve(LocalDate.now());
        reserve.setToleranceMinutes(15);
        reserve.setTimeLimit(LocalDate.now());
        reserve.setStatusReserve(StatusReserveEnum.CONFIRMED);
        restaurant.setReserves(List.of(reserve));

        TableRestaurant tableRestaurant = new TableRestaurant();
        tableRestaurant.setId(1);
        tableRestaurant.setTableIdentification("mesa");
        tableRestaurant.setRestaurantId(1);
        tableRestaurant.setReserveId(1);
        tableRestaurant.setNumberSeats(4);
        tableRestaurant.setStatusTableOccupation(StatusTableOccupationEnum.AVAILABLE);
        tableRestaurant.setTablePosition(TablePositionEnum.EXTERNAL);
        restaurant.setTableRestaurants(List.of(tableRestaurant));

        return restaurant;
    }
    
    
}