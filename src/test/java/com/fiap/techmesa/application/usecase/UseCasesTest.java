package com.fiap.techmesa.application.usecase;


import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.dto.UpdateAddressRequest;
import com.fiap.techmesa.application.dto.UpdateRestaurantRequest;
import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.enums.TablePositionEnum;
import com.fiap.techmesa.application.enums.TypeKitchenEnum;
import com.fiap.techmesa.application.gateway.AddressGateway;
import com.fiap.techmesa.application.gateway.ReserveGateway;
import com.fiap.techmesa.application.gateway.RestaurantGateway;
import com.fiap.techmesa.application.gateway.TableRestaurantGateway;

public class UseCasesTest {

    @Mock
    private AddressGateway addressGateway;
    
    @Mock
    private ReserveGateway reserveGateway;
    
    @Mock
    private RestaurantGateway restaurantGateway;
    
    @Mock
    private TableRestaurantGateway tableRestaurantGateway;
    
    @InjectMocks
    private CreateAddress createAddress;
    
    @InjectMocks
    private GetAddress getAddress;
    
    @InjectMocks
    private CreateReserve createReserve;
    
    @InjectMocks
    private UpdateAddress updateAddress;
    
    @InjectMocks
    private CreateRestaurant createRestaurant;
    
    @InjectMocks
    private UpdateRestaurant updateRestaurant;
    
    @InjectMocks
    private CreateTableRestaurant createTableRestaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAddressTest() {

        Address address = new Address();
        address.setStreet("Test Street");
        address.setNumber(123);
        address.setNeighborhood("Test Neighborhood");
        address.setCity("Test City");
        address.setState("Test State");
        address.setCountry("Test Country");
        address.setCep("123456");

        Address savedAddress = new Address();
        savedAddress.setId(1);
        savedAddress.setStreet("Test Street");
        savedAddress.setNumber(123);
        savedAddress.setNeighborhood("Test Neighborhood");
        savedAddress.setCity("Test City");
        savedAddress.setState("Test State");
        savedAddress.setCountry("Test Country");
        savedAddress.setCep("123456");

        when(addressGateway.save(any(Address.class))).thenReturn(savedAddress);

        Address result = createAddress.execute(address);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Street", result.getStreet());
    }
    
    @Test
    void createReserve() {

        Reserve request = new Reserve();
        request.setClientId(1);
        request.setRestaurantId(1);
        request.setDateReserve(LocalDate.now());

        when(reserveGateway.findByRestaurantIdAndClientIdAndData(1, 1, LocalDate.now())).thenReturn(Optional.empty());
        when(reserveGateway.save(any(Reserve.class))).thenReturn(request);

        Reserve result = createReserve.execute(request, 1);

        assertNotNull(result);
        assertEquals(1, result.getClientId());
        assertEquals(1, result.getRestaurantId());
        assertEquals(LocalDate.now(), result.getDateReserve());
    }
    
    @Test
    void updateAddressTest() {

        Address address = new Address();
        address.setId(1);
        address.setStreet("Old Street");

        UpdateAddressRequest updateRequest = UpdateAddressRequest.builder()
        	    .street("New Street")
        	    .number(123)
        	    .neighborhood("New Neighborhood")
        	    .city("New City")
        	    .state("New State")
        	    .country("New Country")
        	    .cep("12345-678")
        	    .build();

        when(addressGateway.findById(1)).thenReturn(Optional.of(address));
        when(addressGateway.update(any(Address.class))).thenReturn(address);
        
        Address result = updateAddress.execute(1, updateRequest);

        assertNotNull(result);
        assertEquals("New Street", result.getStreet());
        assertEquals(123, result.getNumber());
        assertEquals("New Neighborhood", result.getNeighborhood());
        assertEquals("New City", result.getCity());
        assertEquals("New State", result.getState());
        assertEquals("New Country", result.getCountry());
        assertEquals("12345-678", result.getCep());
    }
    
    @Test
    void createRestaurantTest() {
  
        Restaurant request = new Restaurant();
        request.setId(1);
        request.setName("New Restaurant");

        when(restaurantGateway.findById(1)).thenReturn(Optional.empty());
        when(restaurantGateway.save(any(Restaurant.class))).thenReturn(request);

        Restaurant result = createRestaurant.execute(request);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("New Restaurant", result.getName());
    }
    
    @Test
    void updateRestaurantTest() {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        restaurant.setName("Old Name");

        UpdateRestaurantRequest updateRequest = UpdateRestaurantRequest.builder()
        	    .name("New Name")
        	    .email("newemail@example.com")
        	    .typeKitchen(TypeKitchenEnum.CHINESE)
        	    .capacity(100)
        	    .build();
        when(restaurantGateway.findById(1)).thenReturn(Optional.of(restaurant));
        when(restaurantGateway.update(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant result = updateRestaurant.execute(1, updateRequest);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("newemail@example.com", result.getEmail());
        assertEquals(100, result.getCapacity());
    }
    
    @Test
    void testExecute_TableRestaurantCreatedSuccessfully() {

        TableRestaurant request = new TableRestaurant();
        request.setTableIdentification("Table1");
        request.setRestaurantId(1);
        request.setReserveId(1);
        request.setNumberSeats(4);
        request.setStatusTableOccupation(StatusTableOccupationEnum.AVAILABLE);
        request.setTablePosition(TablePositionEnum.EXTERNAL);

        when(tableRestaurantGateway.findByRestaurantAndDate("Table1", 1)).thenReturn(Optional.empty());
        when(tableRestaurantGateway.save(any(TableRestaurant.class))).thenReturn(request);

        TableRestaurant result = createTableRestaurant.execute(request);

        assertNotNull(result);
        assertEquals("Table1", result.getTableIdentification());
        assertEquals(1, result.getRestaurantId());
        assertEquals(1, result.getReserveId());
        assertEquals(4, result.getNumberSeats());
    }
    
}