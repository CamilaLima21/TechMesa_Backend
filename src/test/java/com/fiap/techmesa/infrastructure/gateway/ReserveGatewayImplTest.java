package com.fiap.techmesa.infrastructure.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.enums.StatusReserveEnum;
import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.enums.TablePositionEnum;
import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.TableRestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.ClientRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.ReserveRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.RestaurantRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.TableRestaurantRepository;

public class ReserveGatewayImplTest {

    @Mock
    private ReserveRepository reserveRepository;

    @Mock
    private ClientRepository clientRepository;
    
    @Mock
    private RestaurantRepository restaurantRepository;
    
    @Mock
    private TableRestaurantRepository tableRestaurantRepository;

    @Spy
    @InjectMocks
    private ReserveGatewayImpl reserveGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        ReserveEntity reserveEntity = new ReserveEntity();
        reserveEntity.setId(1);
        when(reserveRepository.findById(1)).thenReturn(Optional.of(reserveEntity));

        Reserve reserve = new Reserve();
        reserve.setId(1);
        doReturn(reserve).when(reserveGateway).mapToDomain(reserveEntity);

        Optional<Reserve> optionalReserve = reserveGateway.findById(1);
        assertTrue(optionalReserve.isPresent());
        assertEquals(1, optionalReserve.get().getId());
    }

    @Test
    void testSave() {
        Reserve reserve = createReserve();
        ReserveEntity reserveEntity = new ReserveEntity();
        reserveEntity.setId(1);
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1);
        TableRestaurantEntity tableRestaurantEntity = new TableRestaurantEntity();
        tableRestaurantEntity.setId(1);
        when(tableRestaurantRepository.findById(1)).thenReturn(Optional.of(tableRestaurantEntity));
        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurantEntity));
        when(clientRepository.findById(1)).thenReturn(Optional.of(clientEntity));
        when(reserveRepository.save(any(ReserveEntity.class))).thenReturn(reserveEntity);
        doReturn(reserve).when(reserveGateway).mapToDomain(reserveEntity);
        Reserve savedReserve = reserveGateway.save(reserve);
        assertNotNull(savedReserve);
    }

	@Test
	void testDeleteById() {
		doNothing().when(reserveRepository).deleteById(1);
		reserveGateway.delete(1);
		verify(reserveRepository).deleteById(1);
	}
	
	@Test
    void updateTest() {
        Reserve reserve = createReserve();

        ReserveEntity reserveEntity = new ReserveEntity();
        reserveEntity.setId(1);
        reserveEntity.setClient(new ClientEntity());
        reserveEntity.setRestaurant(new RestaurantEntity());
        reserveEntity.setTableRestaurant(new TableRestaurantEntity());

        when(reserveRepository.findById(1)).thenReturn(Optional.of(reserveEntity));
        when(reserveRepository.save(any(ReserveEntity.class))).thenReturn(reserveEntity);
        doReturn(reserve).when(reserveGateway).mapToDomain((ReserveEntity) reserveEntity);

        Reserve updatedReserve = reserveGateway.update(reserve);
        assertNotNull(updatedReserve);
    }
	
	@Test
	void mapToDomainTest() {
	    ClientEntity clientEntity = new ClientEntity();
	    clientEntity.setId(1);

	    RestaurantEntity restaurantEntity = new RestaurantEntity();
	    restaurantEntity.setId(2);

	    TableRestaurantEntity tableRestaurantEntity = new TableRestaurantEntity();
	    tableRestaurantEntity.setId(3);

	    ReserveEntity reserveEntity = ReserveEntity.builder()
	        .id(1)
	        .client(clientEntity)
	        .restaurant(restaurantEntity)
	        .tableRestaurant(tableRestaurantEntity)
	        .numberPeople(5)
	        .dateReserve(LocalDate.now())
	        .dateCreated(LocalDate.now())
	        .startReserve(LocalDateTime.now().toLocalDate())
	        .toleranceMinutes(10)
	        .timeLimit(LocalDate.now())
	        .statusReserve(StatusReserveEnum.CONFIRMED)
	        .build();

        Reserve reserve = reserveGateway.mapToDomain(reserveEntity);

        assertNotNull(reserve);
	}
    
    @Test
    void mapToDomainRestaurantTest() {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1);

        ReserveEntity reserveEntity = new ReserveEntity();
        reserveEntity.setId(2);

        TableRestaurantEntity tableRestaurantEntity = TableRestaurantEntity.builder()
            .id(3)
            .tableIdentification("Table 1")
            .restaurant(restaurantEntity)
            .reserve(reserveEntity)
            .numberSeats(4)
            .statusTableOccupation(StatusTableOccupationEnum.AVAILABLE)
            .tablePosition(TablePositionEnum.INTERNAL)
            .build();

        TableRestaurant tableRestaurant = reserveGateway.mapToDomain(tableRestaurantEntity);
        
        assertNotNull(tableRestaurant);
    }
    
    @Test
    void mapToEntityTest() {
        TableRestaurant tableRestaurant = TableRestaurant.builder()
            .id(3)
            .tableIdentification("Table 1")
            .restaurantId(1)
            .reserveId(2)
            .numberSeats(4)
            .statusTableOccupation(StatusTableOccupationEnum.AVAILABLE)
            .tablePosition(TablePositionEnum.EXTERNAL)
            .build();

        TableRestaurantEntity tableRestaurantEntity = reserveGateway.mapToEntity(tableRestaurant);

        assertNotNull(tableRestaurantEntity);
    }
	
    
    public Reserve createReserve() {
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
        return reserve;
    }
    
    
    
    
}